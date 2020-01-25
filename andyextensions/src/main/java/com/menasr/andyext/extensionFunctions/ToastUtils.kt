@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import com.menasr.andyext.AndyExtApp.Companion.appCtx

/**show short toast with provided stringResourceID*/
fun toastShort(@NonNull @StringRes stringIntId: Int) =
    showToast(
        stringRes(
            stringIntId
        ), Toast.LENGTH_SHORT
    )

/**show short toast with provided
 * @param charSequence string for showing
 * @param gravity gravity for showing the text
 * @param xOffSet for x-axis offset
 * @param yOffSet for y-axis offset*/
fun toastShort(@NonNull charSequence: CharSequence, @NonNull gravity: Int, @NonNull xOffSet: Int = 0, @NonNull yOffSet: Int = 0
) = showToastWithGravity(
    charSequence,
    Toast.LENGTH_SHORT,
    gravity,
    xOffSet,
    yOffSet
)

/**Show toast with string message*/
fun toastShort(@NonNull charSequence: CharSequence) =
    showToast(
        charSequence,
        Toast.LENGTH_SHORT
    )

/**Show long toast with stringResourceID*/
fun toastLong(@NonNull @StringRes stringIntId: Int) =
    showToast(
        stringRes(
            stringIntId
        ), Toast.LENGTH_LONG
    )

/**Show long toast with string message*/
fun toastLong(@NonNull charSequence: CharSequence) =
    showToast(charSequence, Toast.LENGTH_LONG)

/**show long toast with provided
 * @param charSequence string for showing
 * @param gravity gravity for showing the text
 * @param xOffSet for x-axis offset
 * @param yOffSet for y-axis offset*/
fun toastLong(
    @NonNull charSequence: CharSequence,
    @NonNull gravity: Int,
    xOffSet: Int = 0,
    yOffSet: Int = 0
) = showToastWithGravity(
    charSequence,
    Toast.LENGTH_LONG,
    gravity,
    xOffSet,
    yOffSet
)

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