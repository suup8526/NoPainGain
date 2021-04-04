package com.nopaingain.bouldereatout.network.repository

import androidx.lifecycle.MutableLiveData
import com.nopaingain.bouldereatout.network.BoulderEatOutEndPoint
import com.nopaingain.bouldereatout.network.model.auth.LoginRequest
import com.nopaingain.bouldereatout.network.model.auth.LoginResponse
import com.nopaingain.bouldereatout.network.model.auth.RegisterRequest
import com.nopaingain.bouldereatout.network.model.auth.RegisterResponse
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

}