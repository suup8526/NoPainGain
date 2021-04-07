package com.nopaingain.bouldereatout.services

import android.content.Context
import android.content.SharedPreferences
import com.nopaingain.bouldereatout.utils.Constants
import com.nopaingain.bouldereatout.utils.get
import com.nopaingain.bouldereatout.utils.set

class SessionManager(context: Context) {

    var prefs: SharedPreferences = customPrefs(context, Constants.APP_NAME)

    fun customPrefs(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun customPrefs(): SharedPreferences = prefs

    inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * @return true if logged in
     */
    val isLoggedIn: Boolean
        get() = prefs[Constants.IS_LOGGED_IN] ?: false

    /**
     * Used to save Bearer token in prefs
     */
    fun setId(token: String) {
        prefs[Constants.USER_ID] = token
    }

    /**
     * @return Bearer token
     */
    fun getId(): String? = prefs[Constants.USER_ID]

    /**
     * Used to clear prefs on logout
     */
    fun logoutUser() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}