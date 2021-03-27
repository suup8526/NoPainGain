package com.nopaingain.bouldereatout.network

import android.content.Context
import com.nopaingain.bouldereatout.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException

class ErrorHandlerInterceptor(application: Context) : Interceptor {
    var context: Context = application

    override fun intercept(chain: Interceptor.Chain): Response? {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            throw GenericNetworkException(context, ErrorCodes.NO_NETWORK)
        }

        var response: Response? = null

        try {
            response = chain.proceed(chain.request())

            if (response.isSuccessful) {
                return response
            } else {
                val jsonObject = JSONObject(response.body()?.string())
                throw GenericNetworkException(
                    context,
                    ErrorCodes.GENERIC_ERROR,
                    jsonObject.getJSONObject("data").getString("message")
                )
            }

            /*val responseCode = response.code()
            manageException(responseCode)*/
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> manageException(ErrorCodes.NETWORK_TIMEOUT_ERROR)
                is ConnectException -> manageException(ErrorCodes.NETWORK_TIMEOUT_ERROR)
                is SocketException -> manageException(ErrorCodes.NETWORK_TIMEOUT_ERROR)
                is JSONException -> manageException(ErrorCodes.DATA_FORMAT_MISMATCH)
                is GenericNetworkException -> {
                    if (e.errorCode() == ErrorCodes.GENERIC_ERROR) {
                        throw e
                    } else {
                        manageException(e.errorCode())
                    }
                }
                else -> manageException(ErrorCodes.UNKNOWN_ERROR)
            }
        }

        return response
    }

    private fun manageException(responseCode: Int) {
        when (responseCode) {
            400 -> throw  GenericNetworkException(context, ErrorCodes.BAD_INPUT)
            401 -> throw GenericNetworkException(context, ErrorCodes.UN_AUTHORIZED)
            404 -> throw GenericNetworkException(context, ErrorCodes.NO_DATA)
            500 -> throw  GenericNetworkException(context, ErrorCodes.INTERNAL_SERVER_ERROR)

        }
    }

    private fun manageException(errorCodes: ErrorCodes) {
        throw GenericNetworkException(context, errorCodes)
    }
}