package com.nopaingain.bouldereatout.viewmodels

import androidx.lifecycle.MutableLiveData
import com.nopaingain.bouldereatout.BoulderEatOutApplication.Companion.getBoulderEatOutRepository
import com.nopaingain.bouldereatout.network.model.auth.LoginRequest
import com.nopaingain.bouldereatout.network.model.auth.LoginResponse
import com.nopaingain.bouldereatout.network.model.auth.RegisterRequest
import com.nopaingain.bouldereatout.network.model.auth.RegisterResponse
import com.nopaingain.bouldereatout.network.model.common.DataWrapper

class AuthViewModel: BaseViewModel() {

    fun doLogin(loginRequest: LoginRequest): MutableLiveData<DataWrapper<LoginResponse>> {
        return getBoulderEatOutRepository().doLogin(loginRequest)
    }

    fun doRegister(registerRequest: RegisterRequest): MutableLiveData<DataWrapper<RegisterResponse>> {
        return getBoulderEatOutRepository().doRegister(registerRequest)
    }
}