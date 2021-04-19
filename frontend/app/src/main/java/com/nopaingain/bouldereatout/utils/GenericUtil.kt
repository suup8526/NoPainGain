package com.nopaingain.bouldereatout.utils

import android.content.Context
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.nopaingain.bouldereatout.R

fun String.isValidEmail(): Boolean = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun getRatingColor(context: Context, rating: Double): Int {
    return when {
        rating < 2 -> {
            ContextCompat.getColor(context, R.color.rating_1)
        }
        rating < 4 -> {
            ContextCompat.getColor(context, R.color.rating_2)
        }
        else -> {
            ContextCompat.getColor(context, R.color.rating_3)
        }
    }
}