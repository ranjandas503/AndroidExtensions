@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import kotlin.math.hypot

/**
 * Creates a material style circular reveal entrance animation similar to the one in Google Play.
 */
fun View.circularRevealEnter() {
    val cx = width / 2
    val cy = height / 2

    val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

    val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius)

    this.visibility = View.VISIBLE
    anim.start()
}

/**
 * Creates a material style circular reveal exit animation similar to the one in Google Play.
 */
fun View.circularRevealExit() {
    val cx = width / 2
    val cy = height / 2

    val initialRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

    val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, initialRadius, 0f)

    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            visibility = View.INVISIBLE
        }
    })

    anim.start()
}

/**Pulse animation for a view,
 * @param scaleX x axis scale, default is 1.2f
 * @param scaleY y axis scale, default is 1.2f
 * @param duration pulse duration default is 300L*/
fun View.startPulseAnimation(scaleX: Float = 1.2f, scaleY: Float = 1.2f, duration: Long = 300L) {
    val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat("scaleX", scaleX),
        PropertyValuesHolder.ofFloat("scaleY", scaleY)
    )
    scaleDown.duration = duration

    scaleDown.repeatCount = ObjectAnimator.INFINITE
    scaleDown.repeatMode = ObjectAnimator.REVERSE

    scaleDown.start()
}

/**Provide the animation and view , to start the animation,
 * @param animId anim id like R.anim.<id of anim>
 * It will run in main thread*/
fun View.startAnimation(animId: Int) = startAnimation(
    (animRes(
        animId,
        context
    ))
)

/**Provide the animation and view , to start the animation,
 * @param animation animation file for animation
 * It will run in main thread*/
fun View.startAnimating(animation: Animation) = startAnimation(animation)

/**Provide the animation and view , to start the animation,
 * It will run in separate thread
 * @param animId provide the animation id which is present in anim folder*/
fun View.startAnimationInSeparateThread(animId: Int) {
    Handler(Looper.getMainLooper()).post {
        startAnimation(
            (animRes(
                animId,
                context
            ))
        )
    }
}

/**Provide the animation and view , to start the animation,
 * It will run in separate thread
 * @param animation provide the animation id which is present in anim folder*/
fun View.startAnimationInSeparateThread(animation: Animation) {
    Handler(Looper.getMainLooper()).post { startAnimation(animation) }
}