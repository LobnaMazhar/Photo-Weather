package lobna.robusta.photoWeather.interfaces

import android.graphics.Bitmap

/**
 * An interface used to notify capturing an image
 * */
interface CaptureImageInterface {

    /**
     * method to notify caller by capturing an image
     *
     * @param bitmap is the captured image
     * @param rotationDegrees is an integer indicating rotation degrees of the captured image
     * */
    fun imageCaptured(bitmap: Bitmap, rotationDegrees: Int)
}