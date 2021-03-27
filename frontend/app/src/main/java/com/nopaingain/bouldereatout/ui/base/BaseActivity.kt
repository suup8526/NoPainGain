package com.nopaingain.bouldereatout.ui.base

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nopaingain.bouldereatout.utils.hide
import com.nopaingain.bouldereatout.utils.show
import kotlinx.android.synthetic.main.view_progress.*

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getContentViewID(): Int

    abstract fun setupUI()

    /**
     * Show status bar
     */
    fun showStatusBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * Hide status bar
     */
    fun hideStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * show progress dialog
     */
    protected open fun displayProgress() {
        progressLayout?.show()
    }

    /**
     * Hide progress dialog
     */
    protected open fun hideProgress() {
        progressLayout?.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewID())
        setupUI()
    }


}