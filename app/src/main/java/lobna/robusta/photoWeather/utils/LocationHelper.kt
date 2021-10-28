package lobna.robusta.photoWeather.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.widget.Toast
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import lobna.robusta.photoWeather.R
import lobna.robusta.photoWeather.interfaces.GetLocationInterface

class LocationHelper(private val locationInterface: GetLocationInterface) {

    private val TAG = LocationHelper::class.simpleName

    companion object {
        val locationPermissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        const val LOCATION_PERMISSION_CODE = 202
        const val REQUEST_CHECK_SETTINGS = 303
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
            ifPermissionsGranted(activity) -> {
                if (isLocationServicesEnabled(activity))
                    getCurrentLocation(activity, fusedLocationClient)
                else enableLocation(activity) {
                    if (it) getCurrentLocation(activity, fusedLocationClient)
                }
            }
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

    /**
     * Check if Location Services are enabled on the device
     *
     * @param activity Activity requesting location access
     * */
    private fun isLocationServicesEnabled(activity: Activity): Boolean {
        var gpsEnabled = false
        var networkEnabled = false

        val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        try {
            networkEnabled = lm
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return gpsEnabled && networkEnabled
    }

    /**
     * Enable location services on the device
     *
     * @param activity Activity requesting location access
     * */
    private fun enableLocation(activity: Activity, locationEnabled: (Boolean) -> Unit) {
        val locationRequest = LocationRequest.create().apply {
            interval = 30000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        client.checkLocationSettings(builder.build())
            .addOnSuccessListener { locationSettingsResponse ->
                // All location settings are satisfied. The client can initialize
                // location requests from here.
                // ...
                locationEnabled(true)
            }.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        exception.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                }
            }
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