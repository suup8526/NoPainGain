package com.nopaingain.bouldereatout.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.services.SessionManager
import com.nopaingain.bouldereatout.utils.hide
import com.nopaingain.bouldereatout.utils.show
import kotlinx.android.synthetic.main.view_progress.*

abstract class BaseFragment : Fragment() {

    protected lateinit var sessionManager: SessionManager

    abstract fun getLayoutID() : Int

    protected abstract fun init()


    protected abstract fun setupUI()

    protected abstract fun onClick(view: View)

    abstract fun isOnBackPressed(): Boolean

    abstract fun showActionBar(): Boolean

    abstract fun showActionBarBackBtn(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(requireContext())
        if (showActionBar()) {
            (activity as AppCompatActivity).supportActionBar?.show()
        } else {
            (activity as AppCompatActivity).supportActionBar?.hide()
        }
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(showActionBarBackBtn())
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutID(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    protected open fun displayProgress() {
        (activity as BaseActivity).progressLayout?.show()
    }

    protected open fun hideProgress() {
        (activity as BaseActivity).progressLayout?.hide()
    }

    protected val onClickListener = View.OnClickListener {
        onClick(it)
    }

}