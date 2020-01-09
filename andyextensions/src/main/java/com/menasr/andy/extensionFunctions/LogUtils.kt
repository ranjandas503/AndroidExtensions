@file:Suppress("unused", "SpellCheckingInspection")

package com.menasr.andy.extensionFunctions

import android.util.Log
import com.menasr.andy.AndyExtApp

fun logv(anyObject: Any, string: String = "--VERBOSE--") =
    showLog(string, anyObject.toString(), 0)
fun logd(anyObject: Any, string: String = "--DEBUG--") =
    showLog(string, anyObject.toString(), 1)
fun logi(anyObject: Any, string: String = "--INFO--") =
    showLog(string, anyObject.toString(), 2)
fun logw(anyObject: Any, string: String = "--WARNING--") =
    showLog(string, anyObject.toString(), 3)
fun loge(anyObject: Any, string: String = "--ERROR--") =
    showLog(string, anyObject.toString(), 4)
fun logAll(anyObject: Any, string: String = "--ALL--") =
    showLog(string, anyObject.toString(), 5)

private fun showLog(string: String, anyObject: Any, type: Int = 0) {
    if (!AndyExtApp.isLogDisposable)
        when (type) {
            0 -> Log.v(string, anyObject.toString())
            1 -> Log.d(string, anyObject.toString())
            2 -> Log.i(string, anyObject.toString())
            3 -> Log.w(string, anyObject.toString())
            4 -> Log.e(string, anyObject.toString())
            5 -> (0..4).onEach {
                showLog(
                    string,
                    anyObject,
                    it
                )
            }
        }
}
