@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 * @param et pass editText to check if it is empty or not
 */
fun EditText.isEmpty(et: EditText): Boolean = et.text.toString().trim { it <= ' ' }.isNotEmpty()

fun EditText.hideKeyboard() {
    val imm: InputMethodManager? =
        context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun EditText.showKeyboard() {
    if (requestFocus()) {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}
