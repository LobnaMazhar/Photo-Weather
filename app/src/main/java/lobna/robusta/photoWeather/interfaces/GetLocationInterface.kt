package lobna.robusta.photoWeather.interfaces

import com.google.android.gms.maps.model.LatLng

/**
 * An interface used to notify getting location
 * */
interface GetLocationInterface {

    /**
     * method to notify caller by get location
     *
     * @param latLng LatLng Instance of the current location's latitude & longitude, null if location wasn't found
     * */
    fun setLocation(latLng: LatLng?)
}