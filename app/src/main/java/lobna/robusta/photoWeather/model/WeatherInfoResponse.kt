package lobna.robusta.photoWeather.model

data class WeatherInfoResponse(
    val main: MainWeatherModel,
    val sys: SysModel,
    val name: String
)

data class MainWeatherModel(
    val temp: Double,
    val pressure: Int,
    val humidity: Int
)

data class SysModel(
    val country: String
)

