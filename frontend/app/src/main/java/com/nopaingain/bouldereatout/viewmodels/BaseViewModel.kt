package com.nopaingain.bouldereatout.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nopaingain.bouldereatout.network.model.common.ErrorResponse

open class BaseViewModel : ViewModel() {
    var obProcessing = MutableLiveData<Boolean>()
    val obErrorMessage = MutableLiveData<ErrorResponse>()
}