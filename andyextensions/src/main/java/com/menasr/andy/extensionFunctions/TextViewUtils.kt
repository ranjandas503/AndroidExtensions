@file:Suppress("unused")

package com.menasr.andy.extensionFunctions

import android.widget.TextView

/**
 * @param tv pass textView to check if it is empty or not
 */
fun TextView.isEmpty(tv: TextView): Boolean = tv.text.toString().trim { it <= ' ' }.isNotEmpty()