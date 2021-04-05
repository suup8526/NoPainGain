package com.nopaingain.bouldereatout.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.services.SessionManager
import com.nopaingain.bouldereatout.utils.Constants
import com.nopaingain.bouldereatout.utils.set
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthFlowTest {

    private lateinit var scenario: ActivityScenario<AuthActivity>
    private lateinit var prefs: SharedPreferences

    @Before
    fun init() {
        val targetContext = getInstrumentation().targetContext
        val sessionManager = SessionManager(targetContext)
        prefs = sessionManager.prefs
    }

    @After
    fun cleanUp(){
        prefs.edit().clear()
        scenario.close()
    }

    @Test
    fun testLogoutScenario() {
        prefs[Constants.IS_LOGGED_IN] = false
        scenario = launch(AuthActivity::class.java)
        Thread.sleep(3000)
        onView(withId(R.id.btnLogin))?.check(matches(isDisplayed()))
    }

    @Test
    fun testForgotPasswordScreen() {
        prefs[Constants.IS_LOGGED_IN] = false
        scenario = launch(AuthActivity::class.java)
        Thread.sleep(3000)
        onView(withId(R.id.tvForgotPassword))?.perform(ViewActions.click())
        onView(withId(R.id.btnReset))?.check(matches(isDisplayed()))
    }

    @Test
    fun testRegisterScreen() {
        prefs[Constants.IS_LOGGED_IN] = false
        scenario = launch(AuthActivity::class.java)
        Thread.sleep(3000)
        onView(withId(R.id.tvRegister))?.perform(ViewActions.click())
        onView(withId(R.id.btnSubmit))?.check(matches(isDisplayed()))
    }

    @Test
    fun testLoginScenario() {
        prefs[Constants.IS_LOGGED_IN] = true
        scenario = launch(AuthActivity::class.java)
        Thread.sleep(3000)
        onView(withId(R.id.tvDummy))?.check(matches(isDisplayed()))
    }
}