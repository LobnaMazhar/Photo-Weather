package lobna.robusta.photoWeather.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import lobna.robusta.photoWeather.interfaces.GetLocationInterface
import lobna.robusta.photoWeather.utils.LocationHelper

/**
 * Subclass of ViewModel responsible for the logic of get weather info and adding it to the image
 *
 * @property locationInterface instance of [GetLocationInterface] needed for the [LocationHelper] to notify get user's location
 * @property locationHelper has all the logic for getting location
 * */
class WeatherInfoViewModel : ViewModel() {

    private val locationInterface = object : GetLocationInterface {
        override fun setLocation(latLng: LatLng?) {
            // TODO get weather info based on location
        }
    }
    val locationHelper = LocationHelper(locationInterface)
}