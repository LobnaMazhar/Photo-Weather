package lobna.robusta.photoWeather.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import lobna.robusta.photoWeather.utils.IntentHelper.shareToFacebook
import java.io.File
import java.io.FileOutputStream


object IntentHelper {

    private val TAG = IntentHelper::class.simpleName

    fun Context.shareToFacebook(bitmap: Bitmap) {
        try {
            val file = File(externalCacheDir, "weather_photo_${System.currentTimeMillis()}.png")
            FileOutputStream(file).apply {
                bitmap.compress(Bitmap.CompressFormat.PNG, 95, this)
                flush()
                close()
            }

            Intent(Intent.ACTION_SEND).apply {
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val uri =
                    FileProvider.getUriForFile(this@shareToFacebook, "$packageName.provider", file)
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/*"
                setPackage("com.facebook.katana")
                startActivity(Intent.createChooser(this, "Share image on facebook"))
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            Toast.makeText(this, "Error while sharing Image on Facebook", Toast.LENGTH_SHORT).show()
        }
    }

    fun Context.shareToTwitter(bitmap: Bitmap) {
        try {
            val file = File(externalCacheDir, "weather_photo_${System.currentTimeMillis()}.png")
            FileOutputStream(file).apply {
                bitmap.compress(Bitmap.CompressFormat.PNG, 95, this)
                flush()
                close()
            }

            Intent(Intent.ACTION_SEND).apply {
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val uri =
                    FileProvider.getUriForFile(this@shareToTwitter, "$packageName.provider", file)
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/*"
                setPackage("com.twitter.android")
                startActivity(Intent.createChooser(this, "Share image on Twitter"))
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            Toast.makeText(this, "Error while sharing Image on Twitter", Toast.LENGTH_SHORT).show()
        }
    }
}