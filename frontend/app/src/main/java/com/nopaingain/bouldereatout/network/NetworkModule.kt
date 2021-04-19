package com.nopaingain.bouldereatout.network

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nopaingain.bouldereatout.BoulderEatOutApplication
import com.nopaingain.bouldereatout.BuildConfig
import com.nopaingain.bouldereatout.BuildConfig.DEBUG
import com.nopaingain.bouldereatout.utils.Constants
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    fun getRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(makeGson()))
            .client(getOkHttpClient(context))
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    private fun getOkHttpClient(context: Context): OkHttpClient {
        return if (BuildConfig.BUILD_TYPE == "debug") {
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .addInterceptor(ErrorHandlerInterceptor(context))
//                .addInterceptor(ChuckInterceptor(context))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .addInterceptor(ErrorHandlerInterceptor(context))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
        }
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

}