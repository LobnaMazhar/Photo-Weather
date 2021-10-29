package lobna.robusta.photoWeather.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lobna.robusta.photoWeather.model.ImageModel

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageModel)

    @Query("SELECT * FROM WeatherPhoto")
    fun getAll(): List<ImageModel>
}