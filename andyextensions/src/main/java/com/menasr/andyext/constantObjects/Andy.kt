@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.menasr.andyext.constantObjects

import android.content.Context

object Andy {

    /**Get application context of your application anywhere*/
    lateinit var applicationContext: Context

    //log is printed on the basis of this boolean value
    internal var isLogDisposable = false

    //handles double click
    internal var doubleClickDuration = 1000L

    /**Make sure you initialize this function in your application class, Otherwise it will
     * throw null pointer exception*/
    fun initialize(applicationContext: Context) {
        this.applicationContext = applicationContext
    }

    /**Dispose all logs, means all logs are now not be printed
     * @param isDisposable value to notProcess the logs, default = false*/
    fun disposeAllLogs(isDisposable: Boolean = false) {
        isLogDisposable = isDisposable
    }

    /**Dispose all logs, means all logs are now not be printed
     * @param duration this will set a time for next click to available*/
    fun doubleClickDuration(duration: Long) {
        doubleClickDuration = duration
    }
}