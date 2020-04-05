@file:Suppress("unused")

package com.menasr.andyext.constantObjects

import com.menasr.andyext.AndyExtApp

object Andy {

    /**Get application context of your application anywhere*/
    val applicationContext = AndyExtApp.appCtx

    /**Dispose all logs, means all logs are now not be printed
     * @param isDisposable value to notProcess the logs, default = false*/
    fun disposeAllLogs(isDisposable: Boolean = false) {
        AndyExtApp.isLogDisposable = isDisposable
    }

    /**Dispose all logs, means all logs are now not be printed
     * @param duration this will set a time for next click to available*/
    fun doubleClickDuration(duration:Long) {
        AndyExtApp.doubleClickDuration = duration
    }
}