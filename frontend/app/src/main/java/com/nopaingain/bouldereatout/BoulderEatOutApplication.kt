package com.nopaingain.bouldereatout

import android.app.Application
import com.nopaingain.bouldereatout.network.BoulderEatOutEndPoint
import com.nopaingain.bouldereatout.network.NetworkModule
import retrofit2.Retrofit

private var application: BoulderEatOutApplication? = null

class BoulderEatOutApplication : Application() {

    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var eatOutRemoteEndPoint: BoulderEatOutEndPoint

        fun getApplicationContext(): BoulderEatOutApplication {
            return application!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        retrofit = NetworkModule.getRetrofit(baseContext)
        application = this
        eatOutRemoteEndPoint = retrofit.create(BoulderEatOutEndPoint::class.java)
    }
}