package ru.itis.second_sem.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.second_sem.BuildConfig.OPEN_WEATHER_API_URL
import ru.itis.second_sem.data.mapper.WeatherResponseMapper
import ru.itis.second_sem.data.remote.OpenWeatherApi
import ru.itis.second_sem.data.remote.interceptor.AppIdInterceptor
import ru.itis.second_sem.data.repository.WeatherRepositoryImpl
import ru.itis.second_sem.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //это значит что все зависимости будут помещены в синглтон компонент
class DataModule {

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpCleint(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AppIdInterceptor())
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("", "")

                val request = chain.request().newBuilder().url(url.build())
                chain.proceed(request.build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherApi(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): OpenWeatherApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(OPEN_WEATHER_API_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

        return retrofit.create(OpenWeatherApi::class.java)
    }

}