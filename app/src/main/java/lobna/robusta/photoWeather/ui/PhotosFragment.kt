package lobna.robusta.photoWeather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import lobna.robusta.photoWeather.databinding.FragmentPhotosBinding
import lobna.robusta.photoWeather.viewmodel.PhotosViewModel

/**
 * A simple [Fragment] subclass
 * used to view previously taken photos and click on any photo to enlarge it,
 * you can use back button to close the photo
 *
 * */
class PhotosFragment : Fragment() {

    private lateinit var fragmentPhotosBinding: FragmentPhotosBinding

    private val photosViewModel by viewModels<PhotosViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentPhotosBinding = FragmentPhotosBinding.inflate(inflater)
        fragmentPhotosBinding.pvm = photosViewModel
        return fragmentPhotosBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photosViewModel.getImages(requireContext())

        photosViewModel.openPhoto.observe(this, {
            fragmentPhotosBinding.photo.apply {
                visibility = View.VISIBLE
                setImageBitmap(it)
            }
        })
    }

    /**
     * Check if photo is visible
     * */
    fun ifPhotoOpened(): Boolean {
        return fragmentPhotosBinding.photo.isVisible
    }

    /**
     * Close opened photo and get back to list view
     * */
    fun closePhoto() {
        fragmentPhotosBinding.photo.apply {
            visibility = View.GONE
            setImageBitmap(null)
        }
    }
}