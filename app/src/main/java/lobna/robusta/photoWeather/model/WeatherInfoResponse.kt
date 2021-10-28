package lobna.robusta.photoWeather.model

data class WeatherInfoResponse(
    val weather: List<WeatherModel>,
    val main: MainWeatherModel,
    val wind: WindModel,
    val sys: SysModel,
    val name: String
)

data class MainWeatherModel(
    val temp: Double,
    val pressure: Int,
    val humidity: Int
)

data class WeatherModel(
    val main: String,
    val icon: String
)

data class WindModel(
    val speed: Double
)

data class SysModel(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

