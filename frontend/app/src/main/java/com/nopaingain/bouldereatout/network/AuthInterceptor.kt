package com.nopaingain.bouldereatout.network

import android.content.Context
import com.nopaingain.bouldereatout.services.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val context: Context) : Interceptor {

    companion object{
        const val AUTHORIZATION_REQUIRED = "Authorization-required"
        const val AUTHORIZATION_MAYBE = "Authorization-maybe"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SessionManager(context).getId()
        val request = chain.request()

        if (request.header(AUTHORIZATION_REQUIRED) != null) {
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .removeHeader(AUTHORIZATION_REQUIRED)
                .build()
            return chain.proceed(newRequest)
        }

        if (request.header(AUTHORIZATION_MAYBE) != null) {
            val newRequestBuilder = chain.request().newBuilder()
                .removeHeader(AUTHORIZATION_MAYBE)
            if (!token.isNullOrEmpty()) {
                newRequestBuilder.addHeader("Authorization", "Bearer $token")
            }
            return chain.proceed(newRequestBuilder.build())
        }
        return chain.proceed(request)
    }
}