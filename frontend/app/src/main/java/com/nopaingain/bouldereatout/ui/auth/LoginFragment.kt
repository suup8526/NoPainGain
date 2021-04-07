package com.nopaingain.bouldereatout.ui.auth

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.network.model.auth.LoginRequest
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import com.nopaingain.bouldereatout.utils.Constants
import com.nopaingain.bouldereatout.utils.set
import com.nopaingain.bouldereatout.utils.showAlertDialog
import com.nopaingain.bouldereatout.utils.showToast
import com.nopaingain.bouldereatout.viewmodels.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: BaseFragment() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var prefs: SharedPreferences

    override fun getLayoutID(): Int = R.layout.fragment_login

    override fun init() {
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        prefs = sessionManager.customPrefs()
        initObservers()
    }

    private fun initObservers() {
        authViewModel.obProcessing.observe(this, Observer {
            if (it == true){
                displayProgress()
            } else {
                hideProgress()
            }
        })
    }

    override fun setupUI() {
        btnLogin.setOnClickListener(onClickListener)
        tvForgotPassword.setOnClickListener(onClickListener)
        tvRegister.setOnClickListener(onClickListener)
    }

    override fun onClick(view: View) {
        when(view){
            btnLogin -> {
                if (validateFields()) {
                    doLogin()
                }
            }
            tvForgotPassword -> {
                findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
            }
            tvRegister -> {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
    }

    private fun validateFields() : Boolean {
        return when {
            tilUsername.editText?.text?.toString()?.isEmpty() == true -> {
                tilUsername.error = getString(R.string.user_name_empty_msg)
                false
            }
            tilPassword.editText?.text?.toString()?.isEmpty() == true -> {
                tilPassword.error = getString(R.string.password_empty_msg)
                false
            }
            else -> true
        }
    }

    private fun doLogin() {
        authViewModel.obProcessing.value = true
        val loginRequest = LoginRequest(
            username = etUsername?.text?.toString(),
            password = etPassword?.text?.toString()
        )

        authViewModel.doLogin(loginRequest).observe(this, Observer {
            authViewModel.obProcessing.value = false
            it ?: return@Observer
            if (it.responseData?.id != null) {
                sessionManager.setId(it.responseData?.id ?: "")
                prefs[Constants.IS_LOGGED_IN] = true
                context?.showToast(R.string.login_success)
                findNavController().navigate(R.id.action_loginFragment_to_dummyFragment)
                activity?.finish()
            } else {
                context?.showAlertDialog(
                    it.errorResponse?.message ?: getString(R.string.login_error_msg)
                )
            }
        })
    }

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = true

    override fun showActionBarBackBtn(): Boolean = false

}