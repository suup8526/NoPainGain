package com.nopaingain.bouldereatout.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.services.SessionManager
import com.nopaingain.bouldereatout.utils.Constants
import com.nopaingain.bouldereatout.utils.set
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthFlowTest {

    private lateinit var prefs: SharedPreferences

    @Rule
    public val rule = ActivityScenarioRule<AuthActivity>(AuthActivity::class.java)

    @Before
    fun init() {
        rule.scenario.moveToState(Lifecycle.State.CREATED)
        rule.scenario.onActivity {
            val sessionManager = SessionManager(it)
            prefs = sessionManager.prefs
        }
    }

    @Test
    fun testLogoutScenario() {
        prefs[Constants.IS_LOGGED_IN] = false
        onView(withId(R.id.iv_splash))?.check(matches(isDisplayed()))
        onView(isRoot()).perform(waitFor(3000))
        onView(withId(R.id.btnLogin))?.check(matches(isDisplayed()))
    }

    @Test
    fun testLoginScenario() {
        prefs[Constants.IS_LOGGED_IN] = true
        onView(withId(R.id.iv_splash))?.check(matches(isDisplayed()))
        onView(isRoot()).perform(waitFor(3000))
        onView(withId(R.id.tvDummy))?.check(matches(isDisplayed()))
    }
}