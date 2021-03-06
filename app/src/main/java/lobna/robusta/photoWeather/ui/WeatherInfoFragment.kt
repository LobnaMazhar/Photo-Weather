package lobna.robusta.photoWeather.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lobna.robusta.photoWeather.databinding.FragmentWeatherInfoBinding
import lobna.robusta.photoWeather.model.ImageModel
import lobna.robusta.photoWeather.repository.MainRepository
import lobna.robusta.photoWeather.utils.LocationHelper
import lobna.robusta.photoWeather.utils.drawText
import lobna.robusta.photoWeather.utils.spToPx
import lobna.robusta.photoWeather.viewmodel.WeatherInfoViewModel

/**
 * A simple [Fragment] subclass.
 * Used to detect current weather info based on current location and add it to the taken photo
 *
 * @property args Navigation arguments sent from source navigating here, has bitmap of the taken photo
 */
class WeatherInfoFragment : Fragment() {

    private lateinit var fragmentWeatherInfoBinding: FragmentWeatherInfoBinding

    private val args: WeatherInfoFragmentArgs by navArgs()

    private val weatherInfoViewModel by viewModels<WeatherInfoViewModel>()

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private lateinit var bitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentWeatherInfoBinding = FragmentWeatherInfoBinding.inflate(inflater)
        fragmentWeatherInfoBinding.wivm = weatherInfoViewModel
        weatherInfoViewModel.updateImageText.observe(this, {
            if (::bitmap.isInitialized) {
                updateWeatherImage(bitmap.drawText(it, 18f.spToPx(resources.displayMetrics)), true)
            }
        })
        return fragmentWeatherInfoBinding.root
    }

    /**
     * once view is created view the taken photo in an imageview and start getting user's location
     * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (::fragmentWeatherInfoBinding.isInitialized)
            updateWeatherImage(args.weatherImageBitmap)
        startLocation()
    }

    /**
     * Changes weather image view bitmap
     * */
    private fun updateWeatherImage(bitmap: Bitmap, saveImage: Boolean = false) {
        this.bitmap = bitmap
        fragmentWeatherInfoBinding.weatherImageView.apply {
            setImageBitmap(bitmap)
        }
        weatherInfoViewModel.setPhotoBitmap(bitmap)

        if (saveImage) {
            saveImage(bitmap)
        }
    }

    /**
     * Save image to database
     *
     * @param bitmap image's bitmap to save
     * */
    private fun saveImage(bitmap: Bitmap) {
        CoroutineScope(Dispatchers.IO).launch {
            MainRepository.insertImage(requireContext(), ImageModel((bitmap)))
        }
    }

    /**
     * [LocationHelper] class is responsible for getting user's location
     * it needs a running Activity & FusedLocationProviderClient
     * */
    fun startLocation() {
        weatherInfoViewModel.locationHelper.initLocation(requireActivity(), fusedLocationClient)
    }
}