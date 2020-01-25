@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.menasr.andyext.R

/**
 * Method to set Status Bar Color
 *
 * @param color       Resources color id i.e., R.color.'color_name'
 * @param transparent if you want transparency
 *
 */
fun Activity.setStatusBarColor(@ColorInt color: Int, transparent: Boolean = false) {
    if (transparent)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color
}

/**
 * Method to set Status bar bar icon color according to context of your activity form
 * attributes
 */
@RequiresApi(Build.VERSION_CODES.M)
fun Activity.setupStatusBarIconColor() {
    val color = getAttributeColor(
        this,
        R.attr.colorPrimaryDark
    )
    setupStatusBarIconColor(
        isStatusLightColor(color)
    )
}

/**
 * Method to setup Status Bar Icon Color
 *
 * @param isLightToolbar isToolbar is Light Colored
 */
@RequiresApi(Build.VERSION_CODES.M)
fun Activity.setupStatusBarIconColor(isLightToolbar: Boolean) {

    if (getSDKVersionCode() >= Build.VERSION_CODES.M) {
        if ((this as AppCompatActivity).window == null) {
            logAll(
                "Color",
                "setupStatusBarIconColor() getWindow() returns null"
            )
            return
        }

        val view = window.decorView
        if (isLightToolbar) {
            view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            return
        }

        view.systemUiVisibility = 0
    }
}

/**
 * Set Translucent Status Bar
 *
 * @param translucent pass true or false for translucent status
 * **Context should not be null**
 */
fun Activity.setTranslucentStatusBar(translucent: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (translucent) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            return
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}

/**
 * Get Height of Status bar
 */
fun Activity.getStatusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

/**
 * Method to check if status bar is light color or dark
 *
 * @param color just pass the int color to check
 * @return true if color is light or false if dark.
 */
fun isStatusLightColor(@ColorInt color: Int): Boolean {
    val title = getTitleTextColor(color)
    return title != Color.WHITE
}