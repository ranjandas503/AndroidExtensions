@file:Suppress("unused")

package com.menasr.andyext.extensionFunctions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.menasr.andyext.constantObjects.ConstantUtils


/**
 * Throw url
 *
 * @param url in String format
 */
fun getOpenURLIntent(url: String): Intent {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    return intent
}

/**
 * get Call phone intent
 *
 * @param number number to call
 */
fun getCallPhoneIntent(number: String): Intent {
    val callIntent = Intent(Intent.ACTION_DIAL)
    callIntent.data = Uri.parse("tel:$number")
    return callIntent
}

/**
 * Get Send Intent Sms
 *
 * @param number In which sms is to be send
 * @param body   msg body to send
 * @return intent
 */
fun getSendSMSIntent(number: String, body: String): Intent {
    val callIntent = Intent(Intent.ACTION_SENDTO)
    callIntent.data = Uri.parse("sms:$number")
    callIntent.putExtra("sms_body", body)
    return callIntent
}

/**
 * Get GMail Intent
 *
 * @param address email address in which mail is to be send
 * @param subject subject of email
 * @param content body of email
 * @return Intent object
 */
fun getEmailIntent(address: String, subject: String, content: CharSequence): Intent {
    val mailIntent = Intent(Intent.ACTION_SEND)
    mailIntent.type = "message/rfc822"
    mailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
    mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    mailIntent.putExtra(Intent.EXTRA_TEXT, content)
    return mailIntent
}

/**
 * Open a url
 *
 * @param url                      url to open
 * @param selectionTitle           title which is shown selecting options
 * @param exceptionMessageIfOccurs if exception occurs, this will be shown as toast,
 * You can send **null** if you don't want to show any toast
 */
fun Context.openURL(url: String, selectionTitle: String, exceptionMessageIfOccurs: String?) {
    val intent = getOpenURLIntent(url)
    try {
        val chooser = Intent.createChooser(intent, selectionTitle)
        startActivity(chooser)
    } catch (ex: ActivityNotFoundException) {
        if (exceptionMessageIfOccurs != null)
            toastShort(
                exceptionMessageIfOccurs
            )
    }

}

/**
 * Call a phone number
 *
 * @param number                   number in which call to make(use country in pref like 0..etc)
 * @param selectionTitle           title which is shown while navigating to caller screen
 * @param exceptionMessageIfOccurs msg if exception occurs,
 * pass **null** if you don't want to show the toast msg
 */
fun Context.callPhone(number: String, selectionTitle: String, exceptionMessageIfOccurs: String?) {
    val intent = getCallPhoneIntent(number)

    try {
        val chooser = Intent.createChooser(intent, selectionTitle)
        startActivity(chooser)
    } catch (ex: ActivityNotFoundException) {
        if (exceptionMessageIfOccurs != null)
            toastShort(
                exceptionMessageIfOccurs
            )
    }

}

/**
 * Send Sms
 *
 * @param number                   number in which sms is to be send
 * @param body                     message body
 * @param selectionTitle           tile which is set while selecting
 * @param exceptionMessageIfOccurs msg if exception occurs,
 * pass **null** if you don't want to show the toast msg
 */
fun Context.sendSMS(number: String, body: String, selectionTitle: String, exceptionMessageIfOccurs: String?)
{
    val intent =
        getSendSMSIntent(number, body)

    try {
        val chooser = Intent.createChooser(intent, selectionTitle)
        startActivity(chooser)
    } catch (ex: ActivityNotFoundException) {
        if (exceptionMessageIfOccurs != null)
            toastShort(
                exceptionMessageIfOccurs
            )
    }

}

/**
 * Send email
 *
 * @param content                  context of activity
 * @param address                  email address
 * @param subject                  email subject
 * @param content                  body of email
 * @param selectionTitle           selection tile which is shown on the time of selection msg
 * @param exceptionMessageIfOccurs msg if exception occurs,
 * pass **null** if you don't want to show the toast msg
 */
fun Context.sendEmail(address: String, subject: String, content: CharSequence, selectionTitle: String, exceptionMessageIfOccurs: String?)
{
    val mailIntent = getEmailIntent(
        address,
        subject,
        content
    )

    try {
        val chooser = Intent.createChooser(mailIntent, selectionTitle)
        startActivity(chooser)
    } catch (ex: ActivityNotFoundException) {
        if (exceptionMessageIfOccurs != null)
            toastShort(
                exceptionMessageIfOccurs
            )
    }

}

/**
 * Method to navigate user to app's play page
 * Make sure you have active **Internet Connection** before proceeding
 */
fun Context.toGooglePlaystore() {
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

fun Context.isGooglePlayInstalled(): Boolean {
    return try {
        val info =
            packageManager.getPackageInfo("com.android.vending", PackageManager.GET_ACTIVITIES)
        val label = info.applicationInfo.loadLabel(packageManager) as String
        label != "Market"
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}