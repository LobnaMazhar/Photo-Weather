package lobna.robusta.photoWeather.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lobna.robusta.photoWeather.datasource.database.MyRoomDatabase
import lobna.robusta.photoWeather.datasource.network.MyRetrofitClient
import lobna.robusta.photoWeather.datasource.network.WeatherApiInterface
import lobna.robusta.photoWeather.model.ImageModel
import lobna.robusta.photoWeather.model.OpenWeatherResponse

object MainRepository : MainInterface {

    private var weatherApi: WeatherApiInterface =
        MyRetrofitClient.createService(WeatherApiInterface::class.java)

    override suspend fun getWeatherInfo(
        latitude: Double, longitude: Double
    ): OpenWeatherResponse {
        return try {
            val response = weatherApi.currentWeatherInfo(latitude, longitude)

            if (response.isSuccessful) {
                OpenWeatherResponse.DataResponse(response.body())
            } else {
                OpenWeatherResponse.ErrorResponse(response.code(), response.message())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) { e.printStackTrace() }
            OpenWeatherResponse.ExceptionResponse(e.message)
        }
    }

    override suspend fun insertImage(context: Context, image: ImageModel) {
        MyRoomDatabase.invoke(context).photoDao().insert(image)
    }

    override suspend fun getImages(context: Context): List<ImageModel> {
        return MyRoomDatabase.invoke(context).photoDao().getAll()
    }
}