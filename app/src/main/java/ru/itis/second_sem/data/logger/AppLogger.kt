package ru.itis.second_sem.data.logger

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.itis.second_sem.BuildConfig

class AppLogger {

    fun logBody(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        if(BuildConfig.DEBUG) {
            return builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder
    }
}