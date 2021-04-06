package com.nopaingain.bouldereatout.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.nopaingain.bouldereatout.network.ErrorCodes
import com.nopaingain.bouldereatout.network.ErrorHandler
import com.nopaingain.bouldereatout.network.model.common.DataWrapper
import com.nopaingain.bouldereatout.network.model.common.ErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NetworkUtils {

    /**
     * Check if network is available
     * @return true if available else false
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetworkInfo = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetworkInfo)
            capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    /**
     * Generic Network call. Single implementation for all network calls.
     */
    fun <RESPONSE : Any> makeNetworkCall(block: CallHandler<RESPONSE>.() -> Unit): MutableLiveData<DataWrapper<RESPONSE>> =
        CallHandler<RESPONSE>().apply(block).makeCall()

    class CallHandler<RESPONSE : Any> {

        lateinit var retrofitCall: Call<RESPONSE>
        fun makeCall(): MutableLiveData<DataWrapper<RESPONSE>> {
            val result = MutableLiveData<DataWrapper<RESPONSE>>()
            retrofitCall.enqueue(object : Callback<RESPONSE> {
                override fun onFailure(call: Call<RESPONSE>, t: Throwable) {
                    result.postValue(
                        DataWrapper(
                            null,
                            ErrorHandler.handleError(t)
                        )
                    )
                }

                override fun onResponse(call: Call<RESPONSE>, response: Response<RESPONSE>) {
                    when {
                        response.isSuccessful -> result.postValue(
                            DataWrapper(
                                response.body(),
                                null
                            )
                        )
                        else -> result.postValue(
                            DataWrapper(
                                null,
                                ErrorResponse(
                                    response.message(),
                                    ErrorCodes.UNKNOWN_ERROR
                                )
                            )
                        )
                    }

                }
            })
            return result
        }
    }
}