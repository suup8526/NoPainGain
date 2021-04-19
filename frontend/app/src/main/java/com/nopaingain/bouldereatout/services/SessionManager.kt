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
     * Used to save user id in prefs
     */
    fun setId(id: String) {
        prefs[Constants.USER_ID] = id
    }

    /**
     * @return id user id
     */
    fun getId(): String? = prefs[Constants.USER_ID]

    /**
     * Used to save name in prefs
     */
    fun setName(name: String) {
        prefs[Constants.USER_NAME] = name
    }

    /**
     * @return name
     */
    fun getName(): String? = prefs[Constants.USER_NAME]

    /**
     * Used to save email in prefs
     */
    fun setEmail(email: String) {
        prefs[Constants.USER_EMAIL] = email
    }

    /**
     * @return email
     */
    fun getEmail(): String? = prefs[Constants.USER_EMAIL]

    /**
     * Used to clear prefs on logout
     */
    fun logoutUser() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}