@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

@SuppressLint("StaticFieldLeak")
private var snackbar:Snackbar?=null

/**Show snackBar
 * @param currentContext context for snackBar
 * @param msg message which you want to show in snackBar
 * @param snackBarDuration duration for which you want to show a snackBar, default is [Snackbar.LENGTH_SHORT]*/
fun showDefaultSnackBar(currentContext: Context, msg: String, snackBarDuration: Int = Snackbar.LENGTH_SHORT) =
    showSnackBar(
        currentContext,
        msg,
        snackBarDuration
    )


/**Show snackBar
 * @param currentContext context for snackBar
 * @param stringResId string resource id for message, like R.string.<stringID>
 * @param snackBarDuration duration for which you want to show a snackBar, default is [Snackbar.LENGTH_SHORT]*/
fun showDefaultSnackBar(currentContext: Context, @StringRes stringResId: Int, snackBarDuration: Int = Snackbar.LENGTH_SHORT) =
    showSnackBar(
        currentContext,
        stringRes(stringResId),
        snackBarDuration
    )

/**Hide the snackbar, if called through [showDefaultSnackBar](from this class only)*/
fun hideDefaultSnackbar() = snackbar?.dismiss()

private fun showSnackBar(currentContext: Context, msg: String, duration: Int) {
    snackbar = Snackbar.make(
        (currentContext as Activity).findViewById<View>(android.R.id.content),
        msg, duration
    ).also { it.show() }
}