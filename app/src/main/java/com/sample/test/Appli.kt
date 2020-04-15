package com.sample.test

import android.app.Application
import com.menasr.andyext.constantObjects.Andy
import com.menasr.andyext.extensionFunctions.toastLong

class Appli : Application() {

    override fun onCreate() {
        super.onCreate()
        Andy.initialize(applicationContext)
    }
}