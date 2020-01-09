@file:Suppress("unused")

package com.menasr.andy

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

/**
 * Method to set Status Bar Color
 *
 * @param context     context of apllication
 * @param color       Resources color id i.e., R.color.'color_name'
 * @param transparent if you want transparency
 *
 *
 *
 *
 * Make sure context is not null
 */
fun setStatusBarColor(context: Context, @ColorInt color: Int, transparent: Boolean = false) {
    if (transparent)
        (context as Activity).window.addFlags(
            WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
        )
    (context as Activity).window.statusBarColor = color
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

/**
 * Method to set Status bar bar icon color according to context of your activity form
 * attributes
 *
 * @param context just pass the context of your activity
 */
@RequiresApi(Build.VERSION_CODES.M)
fun setupStatusBarIconColor(context: Context?) {
    if (context == null) {
        logAll("Color", "setupStatusBarIconColor() context is null")
        return
    }
    val color = getAttributeColor(context, R.attr.colorPrimaryDark)
    setupStatusBarIconColor(context, isStatusLightColor(color))
}

/**
 * Method to setup Status Bar Incon Color
 *
 * @param context        context of Activity
 * @param isLightToolbar isToolbar is Light Colored
 */
@RequiresApi(Build.VERSION_CODES.M)
fun setupStatusBarIconColor(context: Context, isLightToolbar: Boolean) {

    if (getSDKVersionCode() >= Build.VERSION_CODES.M) {
        if ((context as AppCompatActivity).window == null) {
            logAll("Color", "setupStatusBarIconColor() getWindow() returns null")
            return
        }

        val view = context.window.decorView
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
 * @param context     context of app
 * @param translucent pass true or false for translucent status
 * **Context should not be null**
 */
fun setTranslucentStatusBar(context: Context, translucent: Boolean) {
    if (context !is Activity) {
        logAll("WindowHelper", "context must be instance of activity")
        return
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = context.window
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
 *
 * @param context content of acitivity
 */
fun getStatusBarHeight(context: Context): Int {
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else 0
}