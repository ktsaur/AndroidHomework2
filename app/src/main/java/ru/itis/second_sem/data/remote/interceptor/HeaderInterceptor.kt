package ru.itis.second_sem.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val headers = chain.request().headers.newBuilder()
            .add("Bearer", "")

        val request = chain.request().newBuilder().headers(headers.build())

        return chain.proceed(request.build())
    }
}