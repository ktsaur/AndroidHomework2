package ru.itis.second_sem.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.auth.domain.repository.UserRepository
import ru.itis.second_sem.BuildConfig.OPEN_WEATHER_API_URL
import ru.itis.second_sem.data.database.InceptionDatabase
import ru.itis.second_sem.data.database.dao.UserDao
import ru.itis.second_sem.data.database.migrations.Migration_1_2
import ru.itis.second_sem.data.database.migrations.Migration_2_3
import ru.itis.second_sem.data.database.repository.UserRepositoryImpl
import ru.itis.second_sem.data.logger.AppLogger
import ru.itis.second_sem.data.remote.OpenWeatherApi
import ru.itis.second_sem.data.remote.interceptor.AppIdInterceptor
import ru.itis.second_sem.data.remote.interceptor.MetricInterceptor
import ru.itis.second_sem.di.qualifiers.IoDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //это значит что все зависимости будут помещены в синглтон компонент
class DataModule {

    @Provides
    fun provideAppLogger(): AppLogger {
        return AppLogger()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpCleint(appLogger: AppLogger): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AppIdInterceptor())
            .addInterceptor(MetricInterceptor())
            .apply {
                appLogger.logBody(this)
            }
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

    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext ctx: Context): InceptionDatabase {
        return Room.databaseBuilder(ctx, InceptionDatabase::class.java, DATABASE_NAME)
            .addMigrations(
                Migration_1_2(),
                Migration_2_3()
            )
            .build()
    }

    companion object {
        private const val DATABASE_NAME = "InceptionDB"
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: UserDao,
        @IoDispatchers ioDispatchers: CoroutineDispatcher
    ) : UserRepository {
        return UserRepositoryImpl(userDao = userDao, ioDispatchers = ioDispatchers)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: InceptionDatabase) : UserDao = database.userDao
}