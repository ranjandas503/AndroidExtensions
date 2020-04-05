@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.widget.TextView

/**
 * @param tv pass textView to check if it is empty or not
 */
fun TextView.isEmpty(): Boolean = text.toString().trim { it <= ' ' }.isNotEmpty()

/**This function returns text in EditText*/
fun TextView.textString() = text.toString()

/**Get text by removing space in it*/
fun TextView.trimmedText() = text.toString().replace(" ","")