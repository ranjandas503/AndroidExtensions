@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import com.menasr.andyext.constantObjects.ConstantUtils

/**
 * Method which provides divider between Tab items
 * @param color              int color of line
 * @param width              width of margin
 * @param height             height of margin
 * @param paddingFromDivider padding from divider, it will applied to both side of margin
 */
fun TabLayout.addMarginInTabLayout(color: Int, width: Int, height: Int, paddingFromDivider: Int) {
    val linearLayout = getChildAt(0) as LinearLayout
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

/**Set auto canShow of view, if the view is visible it will hide it or show it*/
fun View.autoVisibility() = visibility(visibility == View.GONE)

/**Reverse the visibility of view, pass your array of views*/
fun autoVisibility(allViews: Array<View>) = allViews.iterator().forEach { it.autoVisibility() }

/**Pass your array of allViews to hide the views*/
fun hide(allViews: Array<View>) = allViews.iterator().forEach { it.hideView() }

/**Pass your array of allViews to show the views*/
fun show(allViews: Array<View>) = allViews.iterator().forEach { it.showView() }

/**Set scroll gestures for layout or view inside a scrollview or scrolling view
 * It uses OnTouchListener and MotionEvent*/
fun View.forceScrollGestures() {
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN ->
                // Disallow ScrollView to intercept touch events.
                v.parent.requestDisallowInterceptTouchEvent(true)

            MotionEvent.ACTION_UP ->
                // Allow ScrollView to intercept touch events.
                v.parent.requestDisallowInterceptTouchEvent(false)
        }

        v.onTouchEvent(event)
        true
    }
}