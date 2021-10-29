package lobna.robusta.photoWeather.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WeatherPhoto")
data class ImageModel(
    @PrimaryKey @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val image: Bitmap
)