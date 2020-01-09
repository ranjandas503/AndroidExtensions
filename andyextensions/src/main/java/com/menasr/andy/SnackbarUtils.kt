@file:Suppress("unused")

package com.menasr.andy

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


fun showSnackShort(
    currentContext: Context,
    msg: String,
    snackBarDuration: Int = Snackbar.LENGTH_SHORT
) =
    showSnackBar(currentContext, msg, snackBarDuration)

fun showSnackShort(
    currentContext: Context, @StringRes stringResId: Int,
    snackBarDuration: Int = Snackbar.LENGTH_SHORT
) =
    showSnackBar(currentContext, stringRes(stringResId), snackBarDuration)

private fun showSnackBar(currentContext: Context, msg: String, duration: Int) {
    Snackbar.make(
        (currentContext as Activity).findViewById<View>(android.R.id.content),
        msg, duration
    ).show()
}