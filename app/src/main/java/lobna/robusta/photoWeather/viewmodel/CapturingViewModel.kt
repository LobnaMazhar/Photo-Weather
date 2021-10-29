package lobna.robusta.photoWeather.viewmodel

import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.ViewModel
import lobna.robusta.photoWeather.interfaces.CaptureImageInterface
import lobna.robusta.photoWeather.utils.CameraHelper
import lobna.robusta.photoWeather.utils.SingleLiveEvent

/**
 * Subclass of ViewModel responsible for the logic of capturing fragment
 *
 * @property captureImageInterface instance of [CaptureImageInterface] needed for the [CameraHelper] to notify image capturing event
 * @property cameraHelper has all the logic for initiating and using camera
 * @property capturedImageBitmap a Single LiveData to notify capturing image
 * @property goToGallery a Single LiveData to notify navigation to gallery
 * */
class CapturingViewModel : ViewModel() {

    private val captureImageInterface = object : CaptureImageInterface {
        override fun imageCaptured(bitmap: Bitmap) {
            capturedImageBitmap.postValue(bitmap)
        }
    }

    val cameraHelper = CameraHelper(captureImageInterface)

    val capturedImageBitmap = SingleLiveEvent<Bitmap>()
    val goToGallery = SingleLiveEvent<Boolean>()

    /**
     * method to ask [cameraHelper] to capture an image
     * called on click action for the capturing button
     * */
    fun takePhoto(view: View) {
        cameraHelper.takePicture(view.context)
    }

    /**
     * navigate to gallery fragment
     * */
    fun showGallery(view: View) {
        goToGallery.postValue(true)
    }
}