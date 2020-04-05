@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.menasr.andyext.AndyExtApp.Companion.appCtx

/**Get string message from string file in android.
 * @param stringId pass the string id like R.string.<id of string>
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun stringRes(@StringRes stringId: Int, context: Context = appCtx) =
    context.getString(stringId)

/**Get string message from string file in android.
 * @param format pass the string format, you can use [stringRes]
 * @param objects objects to format in place of '%d,%s'..etc
 * */
fun getFormattedString(format: String, vararg objects: Any) =
    String.format(format, *objects)

/**Get string message from string file in android.
 * @param stringId pass the string id like R.string.<id of string>
 * @param objects objects to format in place of '%d,%s'..etc
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun getFormattedString(stringId: Int, vararg objects: Any, context: Context = appCtx) =
    String.format(
        stringRes(
            stringId,
            context
        ), *objects
    )

/**Get drawable from resources in android.
 * @param drawableId pass the drawable id like R.drawable.<id of drawable>
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun drawableRes(@DrawableRes drawableId: Int, context: Context = appCtx) =
    ContextCompat.getDrawable(context, drawableId)

/**Get drawable from resources file in android.
 * @param drawableNameWithoutExtension pass your drawable name with out extension
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun drawableByName(drawableNameWithoutExtension: String, context: Context = appCtx): Drawable? {
    val uri = "@drawable/$drawableNameWithoutExtension"
    val imageResource =
        context.resources.getIdentifier(uri, null, context.packageName)
    return ContextCompat.getDrawable(context, imageResource)
}

/**Get color int from color resources file in android.
 * @param colorId pass the color id like R.color.<id of color>
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun colorRes(@ColorRes colorId: Int, context: Context = appCtx) =
    ContextCompat.getColor(context, colorId)

/**Get bitmap from drawable resource file in android.
 * @param drawableId pass the drawable id like R.drawable.<id of drawable>
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun bitmapFromDrawableRes(@DrawableRes drawableId: Int, context: Context = appCtx): Bitmap? =
    BitmapFactory.decodeResource(context.resources, drawableId)

/**Get animation resource file in android.
 * @param animId pass the animation id like R.anim.<id of animation>
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun animRes(@AnimRes animId: Int, context: Context = appCtx): Animation =
    AnimationUtils.loadAnimation(context, animId)

/**Get font resource file in android.
 * @param fontId pass the font id like R.font.<id of font>
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun fontRes(@FontRes fontId: Int, context: Context = appCtx) =
    ResourcesCompat.getFont(context, fontId)

/**Get dimen resource value in android.
 * @param dimenId pass the dimenId id like R.dimen.<id of dimenID>
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun dimen(@DimenRes dimenId: Int, context: Context = appCtx) =
    context.resources.getDimension(dimenId)

/**Get dimen integer value in android.
 * @param dimenIntId pass the dimenId id like R.dimen.<id of dimenIntID>
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun integerRes(@IntegerRes dimenIntId: Int, context: Context = appCtx) =
    context.resources.getInteger(dimenIntId)

/**Get dimen int array value in android.
 * @param dimenArrayIntId pass the dimen int array id like R.dimen.<id of dimenArrayIntID>
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun intArray(@ArrayRes dimenArrayIntId: Int, context: Context = appCtx) =
    context.resources.getIntArray(dimenArrayIntId)

/**Get dimen string array value in android.
 * @param dimenArrayStringId pass the dimen string array like R.dimen.<id of dimenStringArrayID>
 * @param context context is not required, but in case of theme changes Context is required
 * */
fun stringArray(@ArrayRes dimenArrayStringId: Int, context: Context = appCtx): Array<String> =
    context.resources.getStringArray(dimenArrayStringId)