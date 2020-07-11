package com.menasr.andyext.customClasses

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData

/**This class is used for observing network connection
 * @param applicationContext is required with all the permissions
 *
 * just observe on object of <b>NetworkObserver</b>
 *
 * for example like networkObserver.observe(context,Observer())
 * */
class NetworkObserver @RequiresPermission(
    allOf = [Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.INTERNET]
)
constructor(private val applicationContext: Context) : LiveData<Boolean>() {

    private var connectivityManager: ConnectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkCallback: NetworkCallback

    private val CURRENT_VERSION = Build.VERSION.SDK_INT
    private val LOLLIPOP = Build.VERSION_CODES.LOLLIPOP
    private val NOUGAT = Build.VERSION_CODES.N

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        updateConnection()
        when {
            (CURRENT_VERSION >= NOUGAT) -> connectivityManager.registerDefaultNetworkCallback(
                connectivityManagerCallback()
            )
            (CURRENT_VERSION >= LOLLIPOP) -> lollipopNetworkRequest()
            else -> applicationContext.registerReceiver(
                networkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (CURRENT_VERSION >= LOLLIPOP) connectivityManager.unregisterNetworkCallback(connectivityManagerCallback())
        else applicationContext.unregisterReceiver(networkReceiver)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun lollipopNetworkRequest() {
        val requestBuilder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        connectivityManager.registerNetworkCallback(
            requestBuilder.build(),
            connectivityManagerCallback()
        )
    }

    private fun connectivityManagerCallback(): NetworkCallback {
        if (CURRENT_VERSION >= LOLLIPOP) {
            networkCallback = object : NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }
            }
            return networkCallback
        } else throw IllegalAccessError("Error")
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }

    private val networkReceiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(p0: Context?, p1: Intent?) {
            updateConnection()
        }

    }
}