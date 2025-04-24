package ru.itis.second_sem.di

import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.second_sem.data.remote.OpenWeatherApi
import ru.itis.second_sem.BuildConfig.OPEN_WEATHER_API_URL
import ru.itis.second_sem.data.mapper.WeatherResponseMapper
import ru.itis.second_sem.data.remote.interceptor.AppIdInterceptor
import ru.itis.second_sem.data.repository.WeatherRepositoryImpl
import ru.itis.second_sem.domain.repository.WeatherRepository
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase

object ServiceLocator {
    private var okHttpClient: OkHttpClient? = null
    private var openWeatherApi: OpenWeatherApi? = null
    private var weatherByCityNameUseCase: GetWeatherByCityNameUseCase? = null
    private var weatherRepository: WeatherRepository? = null
    private var mapper = WeatherResponseMapper()

    fun getWeatherUsecase() : GetWeatherByCityNameUseCase {
        if (weatherByCityNameUseCase == null) {
            weatherByCityNameUseCase = GetWeatherByCityNameUseCase(
                weatherRepository = getWeatherRepository(),
                ioDispatcher = Dispatchers.IO
            )
        }
        return weatherByCityNameUseCase ?: throw IllegalStateException()
    }


    fun getWeatherRepository() : WeatherRepository {
        if (weatherRepository == null) {
            weatherRepository = WeatherRepositoryImpl(
                weatherAPI = getOpenWeatherApi(),
                mapper = mapper
            )
        }
        return weatherRepository ?: throw IllegalStateException()
    }


    fun getOpenWeatherApi(): OpenWeatherApi {
        if (openWeatherApi == null) {
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AppIdInterceptor())
                .addInterceptor{ chain ->
                    val url = chain.request().url.newBuilder()
                        .addQueryParameter("", "")

                    val request = chain.request().newBuilder().url(url.build())
                    chain.proceed(request.build())
                }
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
        }
        return openWeatherApi ?: throw IllegalStateException()
    }
}