package com.nopaingain.bouldereatout.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import org.json.JSONArray

/**
 * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
 */
operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { this.putString(key, value) }
        is Int -> edit { this.putInt(key, value) }
        is Boolean -> edit { this.putBoolean(key, value) }
        is Float -> edit { this.putFloat(key, value) }
        is Long -> edit { this.putLong(key, value) }
        is Collection<*> -> edit { this.putString(key, value.toString() ) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

/**
 * finds value on given key.
 * [T] is the type of value
 * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
 */
inline operator fun <reified T : Any> SharedPreferences.get(
    key: String,
    defaultValue: T? = null
): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String ?: "") as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        JSONArray::class -> getString(key, defaultValue as? String ?: "") as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}