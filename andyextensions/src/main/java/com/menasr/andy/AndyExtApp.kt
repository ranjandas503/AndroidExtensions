package com.menasr.andy

import android.app.Application
import android.content.Context

internal class AndyExtApp : Application() {

    companion object {
        //application context for dependencies in the application
        internal lateinit var appCtx: Context

        //log is printed on the basis of this boolean value
        internal var isLogDisposable = false

        //handles double click
        internal var doubleClickDuration = 1000L
    }

    override fun onCreate() {
        super.onCreate()
        appCtx = applicationContext
    }
}