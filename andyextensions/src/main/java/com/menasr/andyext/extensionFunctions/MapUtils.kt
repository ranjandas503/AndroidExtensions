@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.menasr.andyext.constantObjects.ConstantUtils.LOC_MARKER_IN_MAPS_BY_ADDRESS
import com.menasr.andyext.constantObjects.ConstantUtils.LOC_MARKER_IN_MAPS_BY_LATLONG
import com.menasr.andyext.constantObjects.ConstantUtils.MAPS_URL
import com.menasr.andyext.constantObjects.ConstantUtils.SEARCH_PLACES
import com.menasr.andyext.constantObjects.ConstantUtils.SEARCH_PLACES_BY_LOCATION

/**Returns true if Google Maps is present in your device.*/
fun Context.isGoogleMapsInstalled(): Boolean {
    return try {
        packageManager.getApplicationInfo("com.google.android.apps.maps", 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

/**
 * Show provided location maker in google maps
 * pass lat,long
 *
 * @param name name which is shown for that marker
 * @return true if successfully opened google maps, or false if google maps not found
 */
fun Context.showMakerInGoogleMap(latitude: Double?, longitude: Double?, name: String): Boolean {
    val link = String.format(LOC_MARKER_IN_MAPS_BY_LATLONG, latitude, longitude, name)
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    mapIntent.setPackage(MAPS_URL)
    return if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
        true
    } else
        false

}

/**
 * Show provided location maker in google maps
 * pass lat,long
 *
 * @param address address on the basis of which search is performed, to find the address
 * @return true if successfully opened google maps, or false if google maps not found
 */
fun Context.showMakerInGoogleMap(address: String): Boolean {
    val link = String.format(LOC_MARKER_IN_MAPS_BY_ADDRESS, address)
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    mapIntent.setPackage(MAPS_URL)
    return if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
        true
    } else
        false

}

/**
 * Show provided location maker in google maps
 * pass lat,long
 *
 * @param format  string format of which you will send parameters
 * @param objects objects seperated by comma, on the basis of format you defined
 *
 *
 * example : format="geo:0,0?q=%s"
 * objects will be, one string object which will be placed in place of **%s**
 * result will be "geo:0,0?q=<your provided string here>"
 * @return true if successfully opened google maps, or false if google maps not found
</your> */
fun Context.showMakerInGoogleMap(format: String, vararg objects: Any): Boolean {
    val link = String.format(format, *objects)
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    mapIntent.setPackage(MAPS_URL)
    return if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
        true
    } else
        false
}

/**
 * Show provided location maker in google maps
 * pass lat,long
 *
 * @param typeOfPlace like restaurant,hotel..etc
 * @return true if successfully opened google maps, or false if google maps not found
 */
fun Context.searchPlaces(typeOfPlace: String): Boolean {
    val link = String.format(SEARCH_PLACES, typeOfPlace)
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    mapIntent.setPackage(MAPS_URL)
    return if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
        true
    } else
        false

}

/**
 * Show provided location maker in google maps
 * pass lat,long
 *
 * @param typeOfPlace like restaurant,hotel..etc
 * @param latitude    latitude
 * @param longitude   longitude
 * @return true if successfully opened google maps, or false if google maps not found
 */
fun Context.searchPlacesByLatLong(latitude: Double?, longitude: Double?, typeOfPlace: String): Boolean
{
    val link = String.format(SEARCH_PLACES_BY_LOCATION, latitude, longitude, typeOfPlace)
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    mapIntent.setPackage(MAPS_URL)
    return if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
        true
    } else
        false

}

/**
 * Get Map Coordinates
 *
 * @param latitudeFrom          from latitude
 * @param longitudeFrom         from longitude
 * @param latitudeTo            to latitude
 * @param longitudeTo           to longitude
 * @param selectionTitle        title while choosing from multiple maps
 * @param exceptionMessageIfOccurs msg if exception occurs,
 * pass **null** if you don't want to show the toast msg
 */
fun Context.showMapRoute(latitudeFrom: Double, longitudeFrom: Double, latitudeTo: Double, longitudeTo: Double, selectionTitle: String, exceptionMessageIfOccurs: String?
) {
    val intent = getMapRouteIntent(
        latitudeFrom,
        longitudeFrom,
        latitudeTo,
        longitudeTo
    )
    try {
        val chooser = Intent.createChooser(intent, selectionTitle)
        startActivity(chooser)
    } catch (ex: ActivityNotFoundException) {
        if (exceptionMessageIfOccurs != null)
            toastLong(exceptionMessageIfOccurs)
    }

}

/**
 * Get Map Coordinates
 *
 * @param latitude
 * @param longitude
 * @param markerTitle              title/name of route or name
 * @param selectionTitle           title while choosing from multiple maps
 * @param exceptionMessageIfOccurs msg if exception occurs,
 * pass **null** if you don't want to show the toast msg
 */
fun Context.showMapCoordinates(latitude: Double, longitude: Double, markerTitle: String, selectionTitle: String, exceptionMessageIfOccurs: String?) {
    val intent = getMapCoordinatesIntent(
        latitude,
        longitude,
        markerTitle
    )
    try {
        val chooser = Intent.createChooser(intent, selectionTitle)
        startActivity(chooser)
    } catch (ex: ActivityNotFoundException) {
        if (exceptionMessageIfOccurs != null)
            toastLong(exceptionMessageIfOccurs)
    }

}

/**
 * Get Map Coordinates
 *
 * @param latitude
 * @param longitude
 * @param markerTitle title/name of route or name
 * @return Intent
 */
fun getMapCoordinatesIntent(latitude: Double, longitude: Double, markerTitle: String?): Intent {
    var uri = "geo:$latitude,$longitude?q=$latitude,$longitude"
    if (markerTitle != null && markerTitle.isNotEmpty()) {
        uri += "($markerTitle)"
    }
    return Intent(Intent.ACTION_VIEW, Uri.parse(uri))
}

/**
 * Get Map Coordinates
 *
 * @param latitudeFrom
 * @param longitudeFrom
 * @param latitudeTo
 * @param longitudeTo
 */
fun getMapRouteIntent(
    latitudeFrom: Double,
    longitudeFrom: Double,
    latitudeTo: Double,
    longitudeTo: Double
): Intent {
    val uri =
        Uri.parse("http://maps.google.com/maps?saddr=$latitudeFrom,$longitudeFrom&daddr=$latitudeTo,$longitudeTo")
    return Intent(Intent.ACTION_VIEW, uri)
}