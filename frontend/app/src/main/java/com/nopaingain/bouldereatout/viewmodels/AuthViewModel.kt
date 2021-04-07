package com.nopaingain.bouldereatout.viewmodels

import androidx.lifecycle.MutableLiveData
import com.nopaingain.bouldereatout.BoulderEatOutApplication.Companion.getBoulderEatOutRepository
import com.nopaingain.bouldereatout.network.model.auth.*
import com.nopaingain.bouldereatout.network.model.common.DataWrapper

class AuthViewModel : BaseViewModel() {

    fun doLogin(loginRequest: LoginRequest): MutableLiveData<DataWrapper<LoginResponse>> {
        return getBoulderEatOutRepository().doLogin(loginRequest)
    }

    fun doRegister(registerRequest: RegisterRequest): MutableLiveData<DataWrapper<RegisterResponse>> {
        return getBoulderEatOutRepository().doRegister(registerRequest)
    }

    fun doLogout(logoutRequest: LogoutRequest): MutableLiveData<DataWrapper<LogoutResponse>> {
        return getBoulderEatOutRepository().doLogout(logoutRequest)
    }
}