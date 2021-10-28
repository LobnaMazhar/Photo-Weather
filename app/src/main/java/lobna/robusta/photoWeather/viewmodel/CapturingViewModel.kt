package lobna.robusta.photoWeather.viewmodel

import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lobna.robusta.photoWeather.interfaces.CaptureImageInterface
import lobna.robusta.photoWeather.utils.CameraHelper
import lobna.robusta.photoWeather.utils.SingleLiveEvent

/**
 * Subclass of ViewModel responsible for the logic of capturing fragment
 *
 * @property captureImageInterface instance of [CaptureImageInterface] needed for the [CameraHelper] to notify image capturing event
 * @property cameraHelper has all the logic for initiating and using camera
 * @property capturedImage an Single LiveData
 * */
class CapturingViewModel : ViewModel() {

    private val captureImageInterface = object : CaptureImageInterface {
        override fun imageCaptured(bitmap: Bitmap, rotationDegrees: Int) {
            capturedImage.postValue(Pair(bitmap, rotationDegrees))
        }
    }

    val cameraHelper = CameraHelper(captureImageInterface)

    val capturedImage = SingleLiveEvent<Pair<Bitmap, Int>>()

    /**
     * method to ask [cameraHelper] to capture an image
     * called on click action for the capturing button
     * */
    fun takePhoto(view: View) {
        cameraHelper.takePicture(view.context)
    }
}