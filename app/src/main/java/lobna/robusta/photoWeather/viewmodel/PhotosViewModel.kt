package lobna.robusta.photoWeather.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lobna.robusta.photoWeather.interfaces.PhotoInterface
import lobna.robusta.photoWeather.model.ImageModel
import lobna.robusta.photoWeather.repository.MainRepository
import lobna.robusta.photoWeather.ui.PhotosAdapter
import lobna.robusta.photoWeather.utils.SingleLiveEvent

/**
 * Subclass of [ViewModel] responsible for the logic of viewing previously taken photos
 *
 * @property images list of images
 * @property photosAdapter adapter attached to recycler of images
 * */
class PhotosViewModel : ViewModel() {

    private val images = ArrayList<ImageModel>()
    val photosAdapter = PhotosAdapter(images, object : PhotoInterface {
        override fun openPhoto(bitmap: Bitmap) {
            openPhoto.postValue(bitmap)
        }
    })

    val openPhoto = SingleLiveEvent<Bitmap>()

    /**
     * Get stored images
     * */
    fun getImages(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = MainRepository.getImages(context)
            response.let {
                withContext(Dispatchers.Main) {
                    images.addAll(it)
                    photosAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}