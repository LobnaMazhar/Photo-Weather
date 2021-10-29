package lobna.robusta.photoWeather.interfaces

import android.graphics.Bitmap
import android.net.Uri

/**
 * An interface used to notify capturing an image
 * */
interface CaptureImageInterface {

    /**
     * method to notify caller by capturing an image
     *
     * @param bitmap is the captured image
     * */
    fun imageCaptured(bitmap: Bitmap)
}