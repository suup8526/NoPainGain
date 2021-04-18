package com.nopaingain.bouldereatout.ui.auth

import android.content.SharedPreferences
import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.services.SessionManager
import com.nopaingain.bouldereatout.ui.dashboard.RestaurantAdapter
import com.nopaingain.bouldereatout.utils.Constants
import com.nopaingain.bouldereatout.utils.set
import com.nopaingain.bouldereatout.viewmodels.RestaurantViewModel
import org.hamcrest.Description
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class GeneralTest {

    private lateinit var scenario: ActivityScenario<AuthActivity>
    private lateinit var prefs: SharedPreferences

    private fun getCountFromRecyclerView(@IdRes recyclerViewId: Int): Int {
        var count = 0
        val matcher = object : TypeSafeMatcher<View?>() {

            override fun matchesSafely(item: View?): Boolean {
                count = (item as RecyclerView).adapter!!.itemCount
                return true
            }

            override fun describeTo(description: Description?) {

            }
        }
        onView(Matchers.allOf(withId(recyclerViewId), isDisplayed()))
                .check(matches(matcher))
        return count
    }

    private fun getTextFromRecyclerView(@IdRes recyclerViewId: Int, position: Int): String? {
        var name: String? = null
        val matcher = object : TypeSafeMatcher<View?>() {

            override fun matchesSafely(item: View?): Boolean {
                name = ((item as RecyclerView).adapter!! as RestaurantAdapter)
                        .getRestaurantList()[position].name
                return true
            }

            override fun describeTo(description: Description?) {

            }
        }
        onView(Matchers.allOf(withId(recyclerViewId), isDisplayed()))
                .check(matches(matcher))
        return name
    }

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
        prefs[Constants.IS_LOGGED_IN] = false
        scenario = launch(AuthActivity::class.java)
        Thread.sleep(3000)
        onView(withId(R.id.etUsername)).perform(replaceText("testUser"));
        onView(withId(R.id.etPassword)).perform(replaceText("testPass"));
        onView(withId(R.id.btnLogin))?.perform(ViewActions.click())
        onView(withId(R.id.dashboardHostFragment))?.check(matches(isDisplayed()))
    }

    @Test
    fun testListingScreen(){
        prefs[Constants.IS_LOGGED_IN] = true
        scenario = launch(AuthActivity::class.java)
        Thread.sleep(3000)
        onView(withId(R.id.rvRestaurant))?.check(matches(isDisplayed()))
    }

    @Test
    fun testRestaurantDetail(){
        prefs[Constants.IS_LOGGED_IN] = true
        scenario = launch(AuthActivity::class.java)
        Thread.sleep(3000)

        val count = getCountFromRecyclerView(R.id.rvRestaurant)
        val position = (0..count).random()
        val name = getTextFromRecyclerView(R.id.rvRestaurant, position)
        assert(name != null)
        onView(withId(R.id.rvRestaurant))
                .perform(RecyclerViewActions.scrollToPosition<RestaurantAdapter.RestaurantViewHolder>(position))
        onView(withId(R.id.rvRestaurant)).perform(actionOnItemAtPosition<RestaurantAdapter.RestaurantViewHolder>(position, click()))
        onView(withId(R.id.tvRestaurantName))?.check(matches(withText(name)))
    }

    @Test
    fun testLogoutSequence(){
        prefs[Constants.IS_LOGGED_IN] = true
        scenario = launch(AuthActivity::class.java)
        Thread.sleep(3000)
        onView(withId(R.id.miCompose))?.perform(ViewActions.click())
        onView(withId(R.id.authHostFragment))?.check(matches(isDisplayed()))
    }
}