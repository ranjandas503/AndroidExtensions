@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.app.Dialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

/**Method to hide keyboard in dialog only*/
fun Dialog.hideKeyboard() {
    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

/**Method to show keyboard in dialog only*/
fun Dialog.showKeyboard() =
    window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)