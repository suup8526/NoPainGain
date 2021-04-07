package com.nopaingain.bouldereatout.network.repository

import androidx.lifecycle.MutableLiveData
import com.nopaingain.bouldereatout.network.BoulderEatOutEndPoint
import com.nopaingain.bouldereatout.network.model.auth.*
import com.nopaingain.bouldereatout.network.model.common.DataWrapper
import com.nopaingain.bouldereatout.utils.NetworkUtils.makeNetworkCall

class BoulderEatOutRepository(private val remoteApiEndPoint: BoulderEatOutEndPoint) {

    fun doLogin(loginRequest: LoginRequest): MutableLiveData<DataWrapper<LoginResponse>> {
        return makeNetworkCall {
            retrofitCall = remoteApiEndPoint.doLogin(loginRequest)
        }
    }

    fun doRegister(registerRequest: RegisterRequest): MutableLiveData<DataWrapper<RegisterResponse>> {
        return makeNetworkCall {
            retrofitCall = remoteApiEndPoint.doRegister(registerRequest)
        }
    }

    fun doLogout(logoutRequest: LogoutRequest): MutableLiveData<DataWrapper<LogoutResponse>> {
        return makeNetworkCall {
            retrofitCall = remoteApiEndPoint.doLogout(logoutRequest)
        }
    }

}