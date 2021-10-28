package lobna.robusta.photoWeather.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import lobna.robusta.photoWeather.R
import lobna.robusta.photoWeather.interfaces.GetLocationInterface

class LocationHelper(val locationInterface: GetLocationInterface) {

    private val TAG = LocationHelper::class.simpleName

    companion object {
        val locationPermissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val LOCATION_PERMISSION_CODE = 202
    }

    /**
     * setting up location method
     *
     * @param activity running activity requesting location access
     * @param fusedLocationClient FusedLocationProviderClient required to get last available location
     * */
    fun initLocation(activity: Activity, fusedLocationClient: FusedLocationProviderClient) {
        val alertText: String
        when {
            ifPermissionsGranted(activity) -> getCurrentLocation(activity, fusedLocationClient)
            shouldShowRationale(activity).run {
                alertText = this; isNotBlank()
            } -> Toast.makeText(activity, alertText, Toast.LENGTH_SHORT).show()
            else -> requestPermissions(activity)
        }
    }

    /**
     * Check if location permissions are granted
     *
     * @param context running context required to check for permissions
     * */
    private fun ifPermissionsGranted(context: Context): Boolean {
        return PermissionUtil().ifPermissionsGranted(
            context, locationPermissions
        )
    }

    /**
     * Check if location permissions were denied before and should show an alert to the user
     *
     * @param activity running activity required to check for permissions
     * */
    private fun shouldShowRationale(activity: Activity): String {
        return PermissionUtil().shouldShowRationale(
            activity, locationPermissions
        )
    }

    /**
     * Request Location Permissions
     *
     * @param activity Activity requesting location access
     * */
    private fun requestPermissions(activity: Activity) {
        PermissionUtil().requestPermissions(
            activity, locationPermissions, LOCATION_PERMISSION_CODE
        )
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ) {
        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val location = task.result
                location?.let {
                    locationInterface.setLocation(LatLng(location.latitude, location.longitude))
                } ?: run {
                    Toast.makeText(
                        context, context.getString(R.string.failed_to_get_location),
                        Toast.LENGTH_SHORT
                    ).show()
                    locationInterface.setLocation(null)
                }
            } else {
                Toast.makeText(
                    context, context.getString(R.string.failed_to_get_location), Toast.LENGTH_SHORT
                ).show()
                locationInterface.setLocation(null)
            }
        }
    }
}