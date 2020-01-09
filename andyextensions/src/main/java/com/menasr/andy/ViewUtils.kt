@file:Suppress("unused")

package com.menasr.andy

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout

/**
 * Method which provides divider between Tab items
 *
 * @param tabLayout          just pass your tab layout
 * @param color              int color of line
 * @param width              width of margin
 * @param height             height of margin
 * @param paddingFromDivider padding from divider, it will applied to both side of margin
 */
fun addMarginInTabLayout(
    tabLayout: TabLayout, color: Int, width: Int, height: Int,
    paddingFromDivider: Int
) {
    val linearLayout = tabLayout.getChildAt(0) as LinearLayout
    linearLayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
    val drawable = GradientDrawable()
    drawable.setColor(color)
    drawable.setSize(width, height)
    linearLayout.dividerPadding = paddingFromDivider
    linearLayout.dividerDrawable = drawable
}

/**Hide a view*/
fun View.hideView() {
    visibility = ConstantUtils.HIDE_VIEW
}

/**Show a view*/
fun View.showView() {
    visibility = ConstantUtils.SHOW_VIEW
}

/**Show or hide view
 * @param visibility true to show the view and false to hide the view*/
fun View.visibility(visibility: Boolean) = if (visibility) showView() else hideView()

/**Show keyboard on click of view*/
fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}
