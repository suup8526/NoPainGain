package com.nopaingain.bouldereatout.ui.auth

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.network.model.auth.RegisterRequest
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import com.nopaingain.bouldereatout.utils.Constants
import com.nopaingain.bouldereatout.utils.isValidEmail
import com.nopaingain.bouldereatout.utils.set
import com.nopaingain.bouldereatout.utils.showAlertDialog
import com.nopaingain.bouldereatout.viewmodels.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.etPassword
import kotlinx.android.synthetic.main.fragment_registration.etUsername
import kotlinx.android.synthetic.main.fragment_registration.tilPassword
import kotlinx.android.synthetic.main.fragment_registration.tilUsername

class RegistrationFragment : BaseFragment() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var prefs: SharedPreferences

    override fun getLayoutID(): Int = R.layout.fragment_registration

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
        btnSubmit.setOnClickListener(onClickListener)
    }

    @SuppressLint("InvalidAnalyticsName")
    override fun onClick(view: View) {
        when (view) {
            btnSubmit -> {
                if (validateFields()) {
                    val bundle = Bundle()
                    bundle.putString("username", etUsername?.text?.toString())
                    bundle.putString("name", etName?.text?.toString())
                    firebaseAnalytics.logEvent(Constants.EVENT_SIGN_UP, bundle)
                    doRegister()
                }
            }
        }
    }

    private fun validateFields() : Boolean {
        return when {
            tilName.editText?.text?.toString()?.isEmpty() == true -> {
                tilName.error = getString(R.string.name_empty_msg)
                false
            }
            tilEmail.editText?.text?.toString()?.isEmpty() == true -> {
                tilEmail.error = getString(R.string.email_empty_msg)
                false
            }
            tilEmail.editText?.text?.toString()?.isValidEmail() == false -> {
                tilEmail.error = getString(R.string.email_invalid_msg)
                false
            }
            tilUsername.editText?.text?.toString()?.isEmpty() == true -> {
                tilUsername.error = getString(R.string.user_name_empty_msg)
                false
            }
            tilPassword.editText?.text?.toString()?.isEmpty() == true -> {
                tilPassword.error = getString(R.string.password_empty_msg)
                false
            }
            tilConfirmPassword.editText?.text?.toString()?.isEmpty() == true -> {
                tilConfirmPassword.error = getString(R.string.confirm_new_password_empty_msg)
                false
            }
            tilPassword.editText?.text?.toString() != tilConfirmPassword.editText?.text?.toString() -> {
                tilConfirmPassword.error = getString(R.string.password_does_not_match)
                false
            }
            else -> true
        }
    }

    private fun doRegister() {
        authViewModel.obProcessing.value = true
        val registerRequest = RegisterRequest(
            name = etName?.text?.toString(),
            email = etEmail?.text?.toString(),
            username = etUsername?.text?.toString(),
            password = etPassword?.text?.toString()
        )

        authViewModel.doRegister(registerRequest).observe(this, Observer {
            authViewModel.obProcessing.value = false
            it ?: return@Observer
            if (it.responseData?.id != null) {
                context?.showAlertDialog(R.string.register_success)
                findNavController().navigateUp()
            } else {
                context?.showAlertDialog(
                    it.errorResponse?.message ?: getString(R.string.register_error_msg)
                )
            }
        })
    }

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = true

    override fun showActionBarBackBtn(): Boolean = true
}