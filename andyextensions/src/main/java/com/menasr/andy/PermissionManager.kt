package com.menasr.andy

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.menasr.andy.ConstantUtils.PERMISSION_REQ_CODE
import java.util.*

/**Created by Ranjan on 27-Aug-2019
 *
 * This class is responsible for handling all permission in android from lollipop and above.
 *
 * Pass activity along with list to permissions to request user, if you don't provide any permission then
 * it will ask for all permissions which is declared in manifest.
 *
 * Just you have to set 2 things in your Activity
 *
 * override [Activity.onRestart] and [Activity.onRequestPermissionsResult] in your activity and call below method from it,
 * yourObject.[checkAndRequestPermissions] from onRestart respectively
 * yourObject.[checkResult] from onRequestPermissionResult and rest all are set.
 *
 * There are several other options like [canRequestAfterDeny],[canCloseActivityIfDeniedForever],
 * [navigateToSettingForForeverDeniedPermission],[addPermissionListener] and much more, do have a look by
 * yourself.
 *
 * Note :- You get your result in activity i.e.,[Activity.onRequestPermissionsResult], as it uses [ActivityCompat] for permissions,
 * but you can call from fragment also
 * */
@Suppress("MemberVisibilityCanBePrivate")
class PermissionManager(
    private val activity: Activity, private var listPermissionsNeeded: List<String>? = null
) {

    private var permissionListener: PermissionListener? = null
    private var confirmationListener: ConfirmationListener? = null
    private var canRequestAgain: Boolean = false
    private var closeIfForeverDenied: Boolean = false
    private var goToSetting: Boolean = false
    private var confirmation: Boolean = false

    //getting all permissions from manifest
    private val allPermissionInApp: List<String>
        get() {
            val per = ArrayList<String>()
            try {
                val pm = activity.applicationContext.packageManager
                val pi = pm.getPackageInfo(
                    activity.applicationContext.packageName,
                    PackageManager.GET_PERMISSIONS
                )
                val permissionInfo = pi.requestedPermissions
                for (p in permissionInfo) {
                    per.add(p)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return per
        }

    init {
        //if list of permission is null, it will ask for all permissions defined in manifest
        if (listPermissionsNeeded == null)
            listPermissionsNeeded = allPermissionInApp
    }

    /**Responsible for confirmation callback if user forever-denys all permissions,
     * You can show your own dialog or do some more stuff you want.
     * @param confirmation <b>false</b> to get callback of confirmation,default is <b>true</b>
     * Note:- It also requires [goToSetting] value <b>true</b>. So, make sure you pass true in the method*/
    fun confirmationBeforeSetting(confirmation: Boolean): PermissionManager {
        this.confirmation = confirmation
        return this
    }

    /**Responsible for re-requesting permission after user deny(<b>not forever deny</b>) the request
     * @param canRequestAgain <b>true</b> to request again for permission,default is <b>false</b>*/
    fun canRequestAfterDeny(canRequestAgain: Boolean): PermissionManager {
        this.canRequestAgain = canRequestAgain
        return this
    }

    /**Responsible for going back, if user forever denies to ask for permission
     * @param closeIfForeverDenied <b>true</b> to close, default is <b>false</b>*/
    fun canCloseActivityIfDeniedForever(closeIfForeverDenied: Boolean): PermissionManager {
        this.closeIfForeverDenied = closeIfForeverDenied
        return this
    }

    /**Responsible for navigating user to setting screen of the app, where user can provide permission again
     * @param goToSetting <b>true</b> to go to setting,default is <b>false</b>*/
    fun navigateToSettingForForeverDeniedPermission(goToSetting: Boolean): PermissionManager {
        this.goToSetting = goToSetting
        return this
    }

    /**Responsible for callback according to accept or deny permission
     * @param permissionListener add this [PermissionListener] for callback, and for details please
     * refer to the [PermissionListener] class definition i.e., by (ctrl+Q)*/
    fun addPermissionListener(permissionListener: PermissionListener): PermissionManager {
        this.permissionListener = permissionListener
        return this
    }

    /**Responsible for callback according to accept or deny permission
     * @param confirmationListener add this [ConfirmationListener] for callback, and for details please
     * refer to the [ConfirmationListener] class definition i.e., by (ctrl+Q)*/
    fun addConfirmationListener(confirmationListener: ConfirmationListener): PermissionManager {
        this.confirmationListener = confirmationListener
        return this
    }

    /**
     * To initiate checking permission
     * just pass the context of application
     */
    fun checkAndRequestPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            //            List<String> listPermissionsNeeded = setPermission();
            val listPermissionsAssign = ArrayList<String>()
            for (per in listPermissionsNeeded!!) {
                if (ContextCompat.checkSelfPermission(
                        activity.applicationContext,
                        per
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    listPermissionsAssign.add(per)
                }
            }

            if (listPermissionsAssign.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    activity,
                    listPermissionsAssign.toTypedArray(),
                    PERMISSION_REQ_CODE
                )
                return false
            }
        }
        return true
    }

    //Method which is responsible to navigate to setting
    fun navigateToSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    /**Responsible for checking the result which is provided by activity,
     * and taking necessary actions according to it
     * @param requestCode pass your request code which you get in [Activity.onRequestPermissionsResult]
     * @param permissions pass your permissions string list which you get in [Activity.onRequestPermissionsResult]
     * @param grantResults pass your grantResults int array which you get in [Activity.onRequestPermissionsResult]
     * */
    @Synchronized
    fun checkResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQ_CODE -> {

                val perms = HashMap<String, Int>()
                for (permission in listPermissionsNeeded!!) {
                    perms[permission] = PackageManager.PERMISSION_GRANTED
                }
                if (grantResults.isNotEmpty()) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]

                    var isAllGranted = true

                    for (permission in listPermissionsNeeded!!) {
                        if (perms[permission] == PackageManager.PERMISSION_DENIED) {
                            isAllGranted = false
                            break
                        }
                    }

                    if (isAllGranted) {
                        //User has granted all the permissions,
                        if (permissionListener != null)
                            permissionListener?.onAllPermissionGranted()

                    } else {
                        var shouldRequest = false
                        val deniedPermissions = ArrayList<String>()
                        val foreverDeniedPermissions = ArrayList<String>()

                        for (permission in listPermissionsNeeded!!) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(
                                    activity,
                                    permission
                                )
                            ) {
                                if (perms[permission] == PackageManager.PERMISSION_DENIED)
                                    deniedPermissions.add(permission)

                                shouldRequest = deniedPermissions.size > 0
                            } else {
                                if (perms[permission] != PackageManager.PERMISSION_GRANTED)
                                    foreverDeniedPermissions.add(permission)
                            }
                        }
                        if (shouldRequest) {
                            if (canRequestAgain)
                                checkAndRequestPermissions()
                        } else {
                            if (closeIfForeverDenied)
                                activity.onBackPressed()

                            if (goToSetting && !activity.isFinishing) {
                                if (confirmation) confirmationListener?.confirmationBeforeSetting { navigateToSettings() }
                                else navigateToSettings()
                            }
                        }

                        //clearing all fields which are not preset in provided list(i.e., manifest permissions)
                        deniedPermissions.retainAll(listPermissionsNeeded!!)
                        foreverDeniedPermissions.retainAll(listPermissionsNeeded!!)

                        //sending list of denied permissions
                        permissionListener?.onPermissionDeny(
                            deniedPermissions,
                            foreverDeniedPermissions
                        )
                    }
                }
            }
        }
    }

    /**This interface is responsible for providing permissions according to user selection,
     * if you have attached listener for callback i.e.,this method [addPermissionListener]*/
    interface PermissionListener {
        /**This method will be invoked when user accepts all the permission*/
        fun onAllPermissionGranted()

        /**This method will be invoked when you user deny/forever-deny permissions,
         * You will get a string list of denied permissions and forever denied permissions*/
        fun onPermissionDeny(
            deniedPermissions: List<String>,
            foreverDeniedPermissions: List<String>
        )
    }

    /**This is for confirmation callback only, i.e.[addConfirmationListener], if you want permissions callback to, then refer
     * [addPermissionListener] which provided [PermissionListener]*/
    interface ConfirmationListener {
        /**This will will be invoked when user forever-deny the permission and you want to show any
         * confirmation before sending user to App settings
         * @param function it will return [navigateToSettings] function, you just have to invoke it like(<b>function.invoke()</b>)
         * Note:- Please refer [goToSetting] and [confirmationBeforeSetting] also,
         * as it depends on it for callback*/
        fun confirmationBeforeSetting(function: () -> Unit)
    }
}