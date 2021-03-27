package com.nopaingain.bouldereatout.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.Toast
import com.nopaingain.bouldereatout.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.getString(res: Int): String? = resources.getString(res)

/**
 * EditText Extension
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

/**
 * Applies density dimensions to the [Int]
 */
fun Int.toDp(displayMetrics: DisplayMetrics) = toFloat().toDp(displayMetrics).toInt()

/**
 * Applies density dimensions to the [Float]
 */
fun Float.toDp(displayMetrics: DisplayMetrics) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)


fun Context.showToast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showAlertDialog(message: String) {
    try {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok) { dialog, _ -> dialog?.cancel() }
        builder.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.showAlertDialog(message: String, onClickListener: DialogInterface.OnClickListener) {
    try {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok, onClickListener)// { dialog, _ -> dialog?.cancel() }
        builder.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.showAlertDialog(
    message: String,
    positiveButtonTxt: String,
    onPositiveClickListener: DialogInterface.OnClickListener,
    negativeButtonTxt: String,
    onNegativeClickListener: DialogInterface.OnClickListener
) {
    try {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButtonTxt, onPositiveClickListener)
        builder.setNegativeButton(negativeButtonTxt, onNegativeClickListener)
        builder.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.showAlertDialog(
    message: Int,
    positiveButtonTxt: Int,
    onPositiveClickListener: DialogInterface.OnClickListener,
    negativeButtonTxt: Int,
    onNegativeClickListener: DialogInterface.OnClickListener
) {
    try {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButtonTxt, onPositiveClickListener)
        builder.setNegativeButton(negativeButtonTxt, onNegativeClickListener)
        builder.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.showAlertDialog(res: Int) {
    try {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setCancelable(false)
        builder.setMessage(res)
        builder.setPositiveButton(R.string.ok) { dialog, _ -> dialog?.cancel() }
        builder.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.showAlertDialog(res: Int, onClickListener: DialogInterface.OnClickListener) {
    try {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setCancelable(false)
        builder.setMessage(res)
        builder.setPositiveButton(R.string.ok, onClickListener) //{ dialog, _ -> dialog?.cancel() }
        builder.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun Context.showCustomAlertDialog(dialog: Dialog, layout: Int): Dialog {

    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view = inflater.inflate(layout, null, false)
    dialog.setContentView(view)
    dialog.setCancelable(true)
    val window = dialog.window
    window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
    window?.setGravity(Gravity.CENTER)
    dialog.show()
    return dialog
}

fun Context.showListAlertDialog(
    title: String, list: ArrayList<String>,
    onItemClickListener: DialogInterface.OnClickListener
) {
    try {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setItems(list.toTypedArray(), onItemClickListener)
        builder.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}