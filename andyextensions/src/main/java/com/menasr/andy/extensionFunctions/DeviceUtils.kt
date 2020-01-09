@file:Suppress("unused")

package com.menasr.andy.extensionFunctions

import android.Manifest.permission.ACCESS_WIFI_STATE
import android.Manifest.permission.INTERNET
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Base64
import android.view.View
import androidx.annotation.RequiresPermission
import com.menasr.andy.constantObjects.ConstantUtils
import java.io.File
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.security.SignatureException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Returns true if the current layout direction is [View.LAYOUT_DIRECTION_RTL].
 *
 * @return
 *
 *This always returns false on versions below JELLY_BEAN_MR1.
 */
fun isRtlLayout() =
    TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL


/**
 * Method to get Region based on SIM card
 * i.e., it will return country region
 *
 * @return "IN" or this type of country code
 */
fun getRegionFromSimCard(context: Context): String =
    (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).simCountryIso

/**
 * Method which provides boolean result for simcard
 *
 * @param context provide the context
 * @return **ture** if sim card is present in device,
 * **false** if sim card is not present in device
 */
fun isSimPresentInDevice(context: Context): Boolean {
    val tm =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager  //gets the current TelephonyManager
    return tm.simState != TelephonyManager.SIM_STATE_ABSENT
}

/**
 * Return whether device is rooted.
 *
 * @return `true`: yes<br></br>`false`: no
 */
fun isDeviceRooted(): Boolean {
    val su = "su"
    val locations = arrayOf(
        "/system/bin/",
        "/system/xbin/",
        "/sbin/",
        "/system/sd/xbin/",
        "/system/bin/failsafe/",
        "/data/local/xbin/",
        "/data/local/bin/",
        "/data/local/"
    )
    for (location in locations) {
        if (File(location + su).exists()) {
            return true
        }
    }
    return false
}

/**
 * Return the version name of device's system.
 *
 * @return the version name of device's system
 */
fun getSDKVersionName(): String? = Build.VERSION.RELEASE

/**
 * Return version code of device's system.
 *
 * @return version code of device's system
 */
fun getSDKVersionCode() = Build.VERSION.SDK_INT


/**
 * Return the MAC address.
 *
 * Must hold
 * `<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />`,
 * `<uses-permission android:name="android.permission.INTERNET" />`
 *
 * @return the MAC address
 */
@RequiresPermission(allOf = [ACCESS_WIFI_STATE, INTERNET])
fun getMacAddress(context: Context): String {
    return getMacAddress(
        context,
        *((null as Array<String>?)!!)
    )
}

/**
 * Return the MAC address.
 *
 * Must hold
 * `<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />`,
 * `<uses-permission android:name="android.permission.INTERNET" />`
 *
 * @return the MAC address
 */
@RequiresPermission(allOf = [ACCESS_WIFI_STATE, INTERNET])
fun getMacAddress(context: Context, vararg excepts: String): String {
    var macAddress =
        getMacAddressByWifiInfo(context)
    if (isAddressNotInExcepts(
            macAddress,
            *excepts
        )
    ) {
        return macAddress
    }
    macAddress =
        getMacAddressByNetworkInterface()
    if (isAddressNotInExcepts(
            macAddress,
            *excepts
        )
    ) {
        return macAddress
    }
    macAddress = getMacAddressByInetAddress()
    if (isAddressNotInExcepts(
            macAddress,
            *excepts
        )
    ) {
        return macAddress
    }

    return ""
}

private fun isAddressNotInExcepts(address: String, vararg excepts: String): Boolean {
    if (excepts.isEmpty()) {
        return "02:00:00:00:00:00" != address
    }
    for (filter in excepts) {
        if (address == filter) {
            return false
        }
    }
    return true
}

private fun getMacAddressByNetworkInterface(): String {
    try {
        val nis = NetworkInterface.getNetworkInterfaces()
        while (nis.hasMoreElements()) {
            val ni = nis.nextElement()
            if (ni == null || !ni.name.equals("wlan0", ignoreCase = true)) continue
            val macBytes = ni.hardwareAddress
            if (macBytes != null && macBytes.isNotEmpty()) {
                val sb = StringBuilder()
                for (b in macBytes) {
                    sb.append(String.format("%02x:", b))
                }
                return sb.substring(0, sb.length - 1)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return "02:00:00:00:00:00"
}

private fun getMacAddressByInetAddress(): String {
    try {
        val inetAddress = getInetAddress()
        if (inetAddress != null) {
            val ni = NetworkInterface.getByInetAddress(inetAddress)
            if (ni != null) {
                val macBytes = ni.hardwareAddress
                if (macBytes != null && macBytes.isNotEmpty()) {
                    val sb = StringBuilder()
                    for (b in macBytes) {
                        sb.append(String.format("%02x:", b))
                    }
                    return sb.substring(0, sb.length - 1)
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return "02:00:00:00:00:00"
}

private fun getInetAddress(): InetAddress? {
    try {
        val nis = NetworkInterface.getNetworkInterfaces()
        while (nis.hasMoreElements()) {
            val ni = nis.nextElement()
            // To prevent phone of xiaomi return "10.0.2.15"
            if (!ni.isUp) continue
            val addresses = ni.inetAddresses
            while (addresses.hasMoreElements()) {
                val inetAddress = addresses.nextElement()
                if (!inetAddress.isLoopbackAddress) {
                    val hostAddress = inetAddress.hostAddress
                    if (hostAddress.indexOf(':') < 0) return inetAddress
                }
            }
        }
    } catch (e: SocketException) {
        e.printStackTrace()
    }

    return null
}

/**
 * Return the android id of device.
 *
 * @return the android id of device
 */
@SuppressLint("HardwareIds")
fun getAndroidID(context: Context): String {

    val id = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )
    return id ?: ""
}


@SuppressLint("HardwareIds", "MissingPermission")
private fun getMacAddressByWifiInfo(context: Context): String {
    try {
        val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        if (info != null) return info.macAddress
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return "02:00:00:00:00:00"
}

/**
 * Return the manufacturer of the product/hardware.
 *
 * e.g. Xiaomi
 *
 * @return the manufacturer of the product/hardware
 */
fun getManufacturer(): String? = Build.MANUFACTURER

/**
 * Return the model of device.
 *
 * e.g. MI2SC
 *
 * @return the model of device
 */
fun getModel(): String {
    var model: String? = Build.MODEL
    model = model?.trim { it <= ' ' }?.replace("\\s*".toRegex(), "") ?: ""
    return model
}

/**
 * Return an ordered list of ABIs supported by this device. The most preferred ABI is the first
 * element in the list.
 *
 * @return an ordered list of ABIs supported by this device
 */
fun getABIs(): Array<String> = Build.SUPPORTED_ABIS


/**
 * Method to navigate user to app's play page
 * Make sure you have active **Internet Connection** before proceeding
 */
fun Context.toPlaystore() {
    val uri = Uri.parse(ConstantUtils.PLAYSTORELINK + packageName)
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    // To count with Play market back stack, After pressing back button,
    // to taken back to our application, we need to add following flags to intent.
    goToMarket.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(ConstantUtils.RETURN_LINK + packageName)
            )
        )
    }

}

/***
 * Computes RFC 2104-compliant HMAC signature. This can be used to sign the Amazon S3
 * request urls
 *
 * @param data The data to be signed.
 * @param key  The signing key.
 * @return The Base64-encoded RFC 2104-compliant HMAC signature.
 * @throws java.security.SignatureException when signature generation fails
 */
@Throws(SignatureException::class)
fun getHMac(data: String?, key: String): String? {

    if (data == null) {
        throw NullPointerException("Data to be signed cannot be null")
    }

    var result: String? = null
    try {

        @Suppress("LocalVariableName") val HMAC_SHA1_ALGORITHM = "HmacSHA1"

        // get an hmac_sha1 key from the raw key bytes
        val signingKey = SecretKeySpec(key.toByteArray(), HMAC_SHA1_ALGORITHM)

        // get an hmac_sha1 Mac instance &
        // initialize with the signing key
        val mac = Mac.getInstance(HMAC_SHA1_ALGORITHM)
        mac.init(signingKey)

        // compute the hmac on input data bytes
        val digest = mac.doFinal(data.toByteArray())

        if (digest != null) {
            // Base 64 Encode the results
            result = Base64.encodeToString(digest, Base64.NO_WRAP)
        }

    } catch (e: Exception) {
        throw SignatureException("Failed to generate HMAC : " + e.message)
    }

    return result
}