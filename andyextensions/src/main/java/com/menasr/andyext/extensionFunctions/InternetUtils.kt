@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.Manifest
import android.Manifest.permission.*
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.ServiceState
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import com.menasr.andyext.constantObjects.Andy

/**
 * @return Connectivity Manager Object
 */
fun getConnectivityManager(context: Context = Andy.applicationContext): ConnectivityManager? {
    return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}

/**
 * This method checks weather Internet is present or not
 * Weather it is WIFI or Mobile Internet
 * **Make sure you have provided Internet permission in manifest**
 *
 * @return bo true(if network is present)
 * and false(if network is not present)
 */
@Suppress("DEPRECATION")
@RequiresPermission(allOf = [ACCESS_WIFI_STATE, INTERNET, ACCESS_NETWORK_STATE])
fun isNetworkConnected(): Boolean {
    val connectivityManager =
        Andy.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //Build.VERSION_CODES.M is Marshmallow(VERSION 23)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null) {
            return networkInfo.isConnected && (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE)
        }
    } else {
        val n = connectivityManager.activeNetwork
        if (n != null) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(n)

            return networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        }
    }

    return false
}

/**
 * Method to get Network type
 * i.e., either it is mobile, network , wifi and much more
 *
 * @return "wifi" if wifi is using
 */
@RequiresPermission(Manifest.permission.READ_PHONE_STATE)
fun getNetworkType(): String? {
    val mTelephonyManager =
        Andy.applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return when (mTelephonyManager.networkType) {
        TelephonyManager.NETWORK_TYPE_GPRS,
        TelephonyManager.NETWORK_TYPE_EDGE,
        TelephonyManager.NETWORK_TYPE_CDMA,
        TelephonyManager.NETWORK_TYPE_1xRTT,
        TelephonyManager.NETWORK_TYPE_IDEN -> "2G"
        TelephonyManager.NETWORK_TYPE_UMTS,
        TelephonyManager.NETWORK_TYPE_EVDO_0,
        TelephonyManager.NETWORK_TYPE_EVDO_A,
        TelephonyManager.NETWORK_TYPE_HSDPA,
        TelephonyManager.NETWORK_TYPE_HSUPA,
        TelephonyManager.NETWORK_TYPE_HSPA,
        TelephonyManager.NETWORK_TYPE_EVDO_B,
        TelephonyManager.NETWORK_TYPE_EHRPD,
        TelephonyManager.NETWORK_TYPE_HSPAP -> "3G"
        TelephonyManager.NETWORK_TYPE_LTE -> "4G"
        else -> "Unknown"
    }
}

/**Check weather phone is in roaming or not*/
fun checkForRoaming(): Boolean {
    var isRoaming = false
    val telephonyManager = Andy.applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    object : PhoneStateListener() {
        override fun onServiceStateChanged(serviceState: ServiceState) {
            super.onServiceStateChanged(serviceState)
            // In Roaming
            // Not in Roaming
            isRoaming = telephonyManager.isNetworkRoaming
            // You can also check roaming state using this
            // In Roaming
            // Not in Roaming
            isRoaming = serviceState.roaming
        }
    }

    return isRoaming
}