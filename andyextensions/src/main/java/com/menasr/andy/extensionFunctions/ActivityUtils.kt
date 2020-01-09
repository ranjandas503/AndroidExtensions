package com.menasr.andy.extensionFunctions

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


/**Hide your soft input*/
fun Activity.hideKeyboard() {
    val focusedView = this.currentFocus
    if (focusedView != null) {
        val inputMethodManager =
            this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}