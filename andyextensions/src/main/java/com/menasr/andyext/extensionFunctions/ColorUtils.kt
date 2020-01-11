@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.core.graphics.drawable.DrawableCompat
import java.util.*
import kotlin.math.roundToInt

/**
 * Method to get Attribute color
 *
 * @param context context of application
 * @param attr    attributes to be send
 * @return white color context is null, else get int color
 */
@ColorInt
fun getAttributeColor(context: Context, @AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    val theme = context.theme
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}

/**
 * Method to get Title text color for an app,
 * Just pass the color and it will calculate new color according to
 * provided color
 *
 * @param color pass the int color, i.e., you can send it from color resource file.
 * @return int color
 */
@ColorInt
fun getTitleTextColor(@ColorInt color: Int): Int {
    val darkness =
        1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return if (darkness < 0.35) getDarkerColor(
        color,
        0.25f
    ) else Color.WHITE
}

/**
 * Method to get body text color for an app,
 * Just pass the color and it will calculate new color according to
 * provided color
 *
 * @param color pass the int color, i.e., you can send it from color resource file.
 * @return int color
 */
@ColorInt
fun getBodyTextColor(@ColorInt color: Int): Int {
    val title = getTitleTextColor(color)
    return setColorAlpha(title, 0.7f)
}

/**
 * Method to get Darker color from provided color
 *
 * @param color        pass the int color, i.e., you can send it from color resource file.
 * @param transparency set transparency between 0.0f to 1.0f
 * @return int color
 */
@ColorInt
fun getDarkerColor(
    @ColorInt color: Int, @FloatRange(
        from = 0.0,
        to = 1.0
    ) transparency: Float
): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)
    hsv[2] *= transparency
    return Color.HSVToColor(hsv)
}

/**
 * Method to get transparency from provided color
 *
 * @param color pass the int color, i.e., you can send it from color resource file.
 * @param alpha set alpha between 0.0f to 1.0f
 * @return int color
 */
@ColorInt
fun setColorAlpha(@ColorInt color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
    val alpha2 = (Color.alpha(color) * alpha).roundToInt()
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)
    return Color.argb(alpha2, red, green, blue)
}

/**
 * Method to get color state list .i.e.,
 * weather it is **pressed_state** or **focuses_state**
 *
 * @param color just pass the color
 * return ColorStateList
 */
fun getColorStateList(@ColorInt color: Int): ColorStateList {
    val states = arrayOf(
        intArrayOf(android.R.attr.state_pressed),
        intArrayOf(android.R.attr.state_focused),
        intArrayOf()
    )
    val colors = intArrayOf(
        getDarkerColor(
            color,
            0.8f
        ), getDarkerColor(color, 0.8f), color)
    return ColorStateList(states, colors)
}

/**
 * Method to get Checked Color State List
 *
 * @param checked   int checked color
 * @param unchecked unchecked color
 * @return ColorStateList
 */
fun getCheckedColorStateList(@ColorInt unchecked: Int, @ColorInt checked: Int): ColorStateList {
    val states =
        arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))

    val colors = intArrayOf(unchecked, checked)
    return ColorStateList(states, colors)
}


/**
 * Check weather it is a valid color string code or not, like "#000000"
 *
 * @param string pass the string color
 * @return true if valid color else false
 */
fun isValidColor(string: String): Boolean {
    return try {
        Color.parseColor(string)
        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Convert a color to an Alpha Red Green Blue string.
 *
 * i.e. [Color.BLUE] returns the string `#FF0000FF`.
 * @param color a color
 * @return the color as string, or null if `color` is `null`.
 */
fun intToARGBString(@ColorInt color: Int): String {
    return String.format(Locale.getDefault(), "#%08X", color)
}

/**Parse alpha Reg green blue color String to int color
 * @param color pass string color i.e., like <b>#FFFFFF</b>*/
fun argbStringToInt(color: String) = Color.parseColor(color)

/**Parse Reg green blue color String to int color
 * @param color pass string color i.e., like <b>#FFFFFF</b>*/
fun convertColor(color: Int): String {
    return if (color != 0) String.format("#%06X", 0xFFFFFF and color) else ""
}

/**change drawable color
 * @param drawableId pass  your drawable int id
 * @param color int color for applying it to the drawable
 * @return colored drawable*/
fun changeDrawableColor(@DrawableRes drawableId: Int, color: Int) =
    drawableRes(drawableId)?.mutate().also { DrawableCompat.setTint(DrawableCompat.wrap(it!!), color) }

/**change drawable outline color
 * @param drawableId pass  your drawable int id
 * @param color int color for applying it to the drawable
 * @param strokeWidth pass stroke width(default is 2)
 * @return colored drawable*/
fun changeDrawableOutlineColor(@DrawableRes drawableId: Int, color: Int, strokeWidth: Int = 3) =
    drawableRes(drawableId)?.mutate().also { (it as GradientDrawable).setStroke(strokeWidth, color) }