package lobna.robusta.photoWeather.repository

import android.content.Context
import lobna.robusta.photoWeather.model.ImageModel
import lobna.robusta.photoWeather.model.OpenWeatherResponse

interface MainInterface {

    suspend fun getWeatherInfo(latitude: Double, longitude: Double): OpenWeatherResponse

    suspend fun insertImage(context: Context, image: ImageModel)

    suspend fun getImages(context: Context): List<ImageModel>
}