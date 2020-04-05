@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.view.View
import android.view.WindowManager
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

/**This function makes the status bar transparent, Call this function just after <b>setContentView</b> in Activity class.
 * Make sure you are using No Action Bar in themes(present in xml),
 * Note :- Be aware of your color usage, as status bar can only be white and black. So if you are using too dark or
 * too light color. Status bar icons wouldn't be visible.*/
fun Activity.viewOverStatesBar() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        statusBarColor = Color.TRANSPARENT
    }
}