@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.menasr.andy.constantObjects

import android.view.View

object ConstantUtils {

    val CHARACTER_POOL : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    const val PERMISSION_REQ_CODE = 1001
    const val SHOW_VIEW = View.VISIBLE
    const val HIDE_VIEW = View.GONE
    const val MAPS_URL = "com.google.android.apps.maps"
    const val LOC_MARKER_IN_MAPS_BY_LATLONG = "geo:0,0?q=%f,%f(%s)"
    const val LOC_MARKER_IN_MAPS_BY_ADDRESS = "geo:0,0?q=%s"
    const val SEARCH_PLACES = "geo:0,0?q=%s"
    const val SEARCH_PLACES_BY_LOCATION = "geo:%f,%f?q=%s"

    const val NAME_REGEX = "^[a-zA-Z\\s]+"
    const val EMAIL_REGEX1 =
        "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
    const val EMAIL_REGEX2 =
        "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

    const val PLAYSTORELINK = "market://details?id="
    const val RETURN_LINK = "http://play.google.com/store/apps/details?id="

    const val KB = 1024
    const val MB = KB * KB

    const val VIDEO_URL = "http://youtube.com/watch?v="
    const val IMAGE_URL = "http://img.youtube.com/vi/"

    const val DIR_NAME = "r_cache_r"
    const val MEM_CACHE_SIZE = MB * 5

    const val INITIAL_CAPACITY = 16
    const val LOAD_FACTOR = 0.75f
    const val CACHE_FILENAME_PREFIX = "my_cache_"
    const val IO_BUFFER_SIZE = 3 * KB // 3KB
    const val MAX_REMOVALS = 4
    const val DISK_MAX_CACHE_ITEM_COUNT = 32
    const val DISK_MAX_CACHE_BYTE_SIZE = (MB * 10).toLong()  // 10MB

    const val BUFFER_LEN = 8192

    /**
     * Regex of simple mobile.
     */
    const val REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$"

    /**
     * Regex of telephone number.
     */
    const val REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}"

    /**
     * Regex of email.
     */
    const val REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"

    /**
     * Regex of url.
     */
    const val REGEX_URL = "[a-zA-z]+://[^\\s]*"

    /**
     * Regex of username.
     *
     * scope for "a-z", "A-Z", "0-9", "_", "Chinese character"
     *
     * can't end with "_"
     *
     * length is between 6 to 20
     */
    const val REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$"

    /**
     * Regex of date which pattern is "yyyy-MM-dd".
     */
    const val REGEX_DATE =
        "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$"

    /**
     * Regex of ip address.
     */
    const val REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)"

    /**
     * Regex of double-byte characters.
     */
    const val REGEX_DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]"

    /**
     * Regex of blank line.
     */
    const val REGEX_BLANK_LINE = "\\n\\s*\\r"

    /**
     * Regex of QQ number.
     */
    const val REGEX_QQ_NUM = "[1-9][0-9]{4,}"

    /**
     * Regex of positive integer.
     */
    const val REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$"

    /**
     * Regex of negative integer.
     */
    const val REGEX_NEGATIVE_INTEGER = "^-[1-9]\\d*$"

    /**
     * Regex of integer.
     */
    const val REGEX_INTEGER = "^-?[1-9]\\d*$"

    /**
     * Regex of non-negative integer.
     */
    const val REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$"

    /**
     * Regex of non-positive integer.
     */
    const val REGEX_NOT_POSITIVE_INTEGER = "^-[1-9]\\d*|0$"

    /**
     * Regex of positive float.
     */
    const val REGEX_POSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$"

    /**
     * Regex of negative float.
     */
    const val REGEX_NEGATIVE_FLOAT = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$"

}