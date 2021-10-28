package lobna.robusta.photoWeather.repository

import lobna.robusta.photoWeather.model.OpenWeatherResponse

interface MainInterface {

    suspend fun getWeatherInfo(latitude: Double, longitude: Double): OpenWeatherResponse
}