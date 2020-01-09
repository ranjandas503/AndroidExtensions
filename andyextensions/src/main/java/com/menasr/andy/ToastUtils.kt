@file:Suppress("unused")

package com.menasr.andy

import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import com.menasr.andy.AndyExtApp.Companion.appCtx

fun toastShort(@NonNull @StringRes stringIntId: Int) =
    showToast(stringRes(stringIntId), Toast.LENGTH_SHORT)

fun toastShort(
    @NonNull charSequence: CharSequence,
    @NonNull gravity: Int,
    xOffSet: Int = 0,
    yOffSet: Int = 0
) = showToastWithGravity(charSequence, Toast.LENGTH_SHORT, gravity, xOffSet, yOffSet)

fun toastShort(@NonNull charSequence: CharSequence) =
    showToast(charSequence, Toast.LENGTH_SHORT)

fun toastLong(@NonNull @StringRes stringIntId: Int) =
    showToast(stringRes(stringIntId), Toast.LENGTH_LONG)

fun toastLong(@NonNull charSequence: CharSequence) =
    showToast(charSequence, Toast.LENGTH_LONG)

fun toastLong(
    @NonNull charSequence: CharSequence,
    @NonNull gravity: Int,
    xOffSet: Int = 0,
    yOffSet: Int = 0
) = showToastWithGravity(charSequence, Toast.LENGTH_LONG, gravity, xOffSet, yOffSet)

private fun showToast(msg: CharSequence, toastDuration: Int) =
    Toast.makeText(appCtx, msg, toastDuration).show()

private fun showToastWithGravity(
    msg: CharSequence,
    toastDuration: Int,
    gravity: Int,
    xOffSet: Int,
    yOffSet: Int
) {
    val toast = Toast.makeText(appCtx, msg, toastDuration)
    toast.setGravity(gravity, xOffSet, yOffSet)
    toast.show()
}