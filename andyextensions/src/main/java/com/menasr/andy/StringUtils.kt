@file:Suppress("unused")

package com.menasr.andy

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import androidx.annotation.DrawableRes

/**
 * Method to convert String to ArrayList
 *
 * @param splitter splitter on the basis of which, string is converted to Array
 * i.e., like **',',':'..etc**
 * @Note . is not converted
 */
fun String.convertToArray(splitter: String): List<*> =
    listOf(*this.split(splitter.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())

fun String.isValidEmail(): Boolean = matches(ConstantUtils.EMAIL_REGEX1.toRegex()) && matches(ConstantUtils.EMAIL_REGEX2.toRegex())

fun String.isValidName(): Boolean = matches(ConstantUtils.NAME_REGEX.toRegex())

fun String.upperFirstLetter(): String {
    return if (!Character.isLowerCase(this[0])) this else (this[0].toInt() - 32).toChar().toString() + this.substring(
        1
    )
}

fun String.lowerFirstLetter(): String {
    return if (!Character.isUpperCase(this[0])) this else (this[0].toInt() + 32).toChar().toString() + this.substring(
        1
    )
}

/**Get character sequence for an icon
 * @param drawableResourceId drawable id for getting character sequence
 * @return character sequence of the image*/
fun getIconifiedTitle(@DrawableRes drawableResourceId: Int): CharSequence {
    val sb = SpannableStringBuilder(" ")

    val drawable = drawableRes(drawableResourceId)
    if(drawable!=null) {
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val span = ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM)
        sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return sb
}