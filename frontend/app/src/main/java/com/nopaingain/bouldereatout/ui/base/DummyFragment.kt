package com.nopaingain.bouldereatout.ui.base

import android.view.View
import com.nopaingain.bouldereatout.R

class DummyFragment : BaseFragment() {

    override fun getLayoutID(): Int = R.layout.fragment_dummy

    override fun init() {

    }

    override fun setupUI() {

    }

    override fun onClick(view: View) {

    }

    override fun isOnBackPressed(): Boolean = false

    override fun showActionBar(): Boolean = false

    override fun showActionBarBackBtn(): Boolean = false

}