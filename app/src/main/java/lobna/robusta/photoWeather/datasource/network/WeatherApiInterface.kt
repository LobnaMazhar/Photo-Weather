package lobna.robusta.photoWeather.datasource.network

import lobna.robusta.photoWeather.BuildConfig
import lobna.robusta.photoWeather.model.WeatherInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {

    @GET("weather")
    suspend fun currentWeatherInfo(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): Response<WeatherInfoResponse>
}