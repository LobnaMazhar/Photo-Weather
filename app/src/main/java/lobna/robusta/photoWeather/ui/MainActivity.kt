package lobna.robusta.photoWeather.ui

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import lobna.robusta.photoWeather.R
import lobna.robusta.photoWeather.utils.CameraHelper
import lobna.robusta.photoWeather.utils.PermissionUtil

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val navHostFragment: Fragment? by lazy { supportFragmentManager.primaryNavigationFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.fragment_container_view)
    }

    /**
     * Results of requesting camera permission
     * if [grantResults] wasn't empty and has a value of [PackageManager.PERMISSION_GRANTED]
     * then camera permissions were granted and user can continue with the logic
     * if else, user should grant the permissions otherwise he won't be able to use the app
     * */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CameraHelper.CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    navHostFragment?.childFragmentManager?.fragments?.get(0)?.apply {
                        if (this is CapturingFragment) startCamera()
                    }
                } else {
                    Toast.makeText(
                        this, getString(R.string.permission_not_granted), Toast.LENGTH_SHORT
                    ).show()

                    PermissionUtil.requestPermissions(this)
                }
            }
        }
    }
}