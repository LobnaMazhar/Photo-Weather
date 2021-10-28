package lobna.robusta.photoWeather.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lobna.robusta.photoWeather.interfaces.GetLocationInterface
import lobna.robusta.photoWeather.model.OpenWeatherResponse
import lobna.robusta.photoWeather.model.WeatherInfoResponse
import lobna.robusta.photoWeather.repository.MainRepository
import lobna.robusta.photoWeather.utils.LocationHelper

/**
 * Subclass of [AndroidViewModel] responsible for the logic of get weather info and adding it to the image
 *
 * @property locationInterface instance of [GetLocationInterface] needed for the [LocationHelper] to notify get user's location
 * @property locationHelper has all the logic for getting location
 * */
class WeatherInfoViewModel(application: Application) : AndroidViewModel(application) {

    val isLoadingObservable = ObservableBoolean(true)

    private val locationInterface = object : GetLocationInterface {
        override fun setLocation(latLng: LatLng?) {
            /**
             * Get weather info based on current location if not null, else dismiss loading
             * */
            latLng?.run { getWeatherInfo(latitude, longitude) }
                ?: run { isLoadingObservable.set(false) }
        }
    }
    val locationHelper = LocationHelper(locationInterface)

    /**
     * Ask [MainRepository] class to fetch weather info based on given data
     *
     * @param latitude Latitude of location
     * @param longitude Longitude of location
     * */
    private fun getWeatherInfo(latitude: Double, longitude: Double) {
        isLoadingObservable.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = MainRepository.getWeatherInfo(latitude, longitude)
            withContext(Dispatchers.Main) { bindResponse(response) }
        }
    }

    /**
     * Parse return response from fetching weather info
     * */
    private fun bindResponse(response: OpenWeatherResponse) {
        isLoadingObservable.set(false)
        when (response) {
            is OpenWeatherResponse.ErrorResponse ->
                Toast.makeText(getApplication(), response.message, Toast.LENGTH_LONG).show()
            is OpenWeatherResponse.ExceptionResponse ->
                Toast.makeText(getApplication(), response.message, Toast.LENGTH_LONG).show()
            is OpenWeatherResponse.DataResponse<*> -> {
                (response.data as? WeatherInfoResponse)?.run {
                    // TODO add data to image
                }
            }
        }
    }
}