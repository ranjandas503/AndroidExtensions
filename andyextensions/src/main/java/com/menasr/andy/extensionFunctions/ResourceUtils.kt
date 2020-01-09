@file:Suppress("unused")

package com.menasr.andy.extensionFunctions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.menasr.andy.AndyExtApp.Companion.appCtx

fun stringRes(@StringRes stringId: Int, context: Context = appCtx) =
    context.getString(stringId)

fun getFormattedString(format: String, vararg objects: Any) =
    String.format(format, *objects)

fun getFormattedString(stringId: Int, vararg objects: Any, context: Context = appCtx) =
    String.format(
        stringRes(
            stringId,
            context
        ), *objects)

fun drawableRes(@DrawableRes drawableId: Int, context: Context = appCtx) =
    ContextCompat.getDrawable(context, drawableId)

fun drawableByName(drawableNameWithoutExtension: String, context: Context = appCtx): Drawable? {
    val uri = "@drawable/$drawableNameWithoutExtension"
    val imageResource =
        context.resources.getIdentifier(uri, null, context.packageName)
    return ContextCompat.getDrawable(context, imageResource)
}

fun colorRes(@ColorRes colorId: Int, context: Context = appCtx) =
    ContextCompat.getColor(context, colorId)

fun bitmapFromDrawableRes(@DrawableRes drawableId: Int, context: Context = appCtx): Bitmap? =
    BitmapFactory.decodeResource(context.resources, drawableId)

fun animRes(@AnimRes animId: Int, context: Context = appCtx): Animation =
    AnimationUtils.loadAnimation(context, animId)

fun fontRes(@FontRes fontId: Int, context: Context = appCtx) =
    ResourcesCompat.getFont(context, fontId)

fun dimen(@DimenRes dimenId: Int, context: Context = appCtx) =
    context.resources.getDimension(dimenId)

fun dimenInteger(@IntegerRes dimenIntId: Int, context: Context = appCtx) =
    context.resources.getInteger(dimenIntId)

fun dimenIntArray(@ArrayRes dimenArrayIntId: Int, context: Context = appCtx) =
    context.resources.getIntArray(dimenArrayIntId)

fun dimenStringArray(@ArrayRes dimenArrayStringId: Int, context: Context = appCtx): Array<String> =
    context.resources.getStringArray(dimenArrayStringId)