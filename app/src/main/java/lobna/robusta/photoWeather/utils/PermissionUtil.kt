package lobna.robusta.photoWeather.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import lobna.robusta.photoWeather.R

/**
 * A singleton class used to ask for required permissions
 * */
class PermissionUtil {

    /**
     * Check if permissions are granted
     *
     * @param context running context
     * @param permissions list of permissions to check for
     * */
    fun ifPermissionsGranted(context: Context, permissions: List<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * If user has denied permission before, show an alert why he should allow the permission
     *
     * @param activity running activity
     * @param permissions list of permissions to check for
     * */
    fun shouldShowRationale(activity: Activity, permissions: List<String>): String {
        val alertText = StringBuffer()
        permissions.forEach {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, it)) {
                alertText.appendLine(getPermissionAlertText(activity, it))
            }
        }
        return alertText.toString()
    }

    /**
     * Get alert string for permission
     *
     * @param context running context
     * @param permission permission to get alert text for
     * */
    private fun getPermissionAlertText(context: Context, permission: String): String {
        return when (permission) {
            Manifest.permission.CAMERA -> context.getString(R.string.camera_permission)
            else -> ""
        }
    }

    /**
     * Request required permissions
     *
     * @param activity the running activity that asks for the permissions
     * @param permissions List of needed permissions
     * @param requestCode request code for permissions
     * */
    fun requestPermissions(activity: Activity, permissions: List<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(
            activity, permissions.toTypedArray(), requestCode
        )
    }
}