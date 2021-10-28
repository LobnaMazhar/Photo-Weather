package lobna.robusta.photoWeather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.LocationServices
import lobna.robusta.photoWeather.databinding.FragmentWeatherInfoBinding
import lobna.robusta.photoWeather.utils.LocationHelper
import lobna.robusta.photoWeather.viewmodel.WeatherInfoViewModel

/**
 * A simple [Fragment] subclass.
 * Used to detect current weather info based on current location and add it to the taken photo
 *
 * @property args Navigation arguments sent from source navigating here,
 * has bitmap and rotation degrees of the taken photo
 */
class WeatherInfoFragment : Fragment() {

    lateinit var fragmentWeatherInfoBinding: FragmentWeatherInfoBinding

    private val args: WeatherInfoFragmentArgs by navArgs()

    private val weatherInfoViewModel by viewModels<WeatherInfoViewModel>()

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentWeatherInfoBinding = FragmentWeatherInfoBinding.inflate(inflater)
        return fragmentWeatherInfoBinding.root
    }

    /**
     * once view is created view the taken photo in an imageview and start getting user's location
     * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (::fragmentWeatherInfoBinding.isInitialized)
            fragmentWeatherInfoBinding.weatherImageView.apply {
                setImageBitmap(args.weatherImage)
                rotation = args.rotationDegrees * 1f
            }

        startLocation()
    }

    /**
     * [LocationHelper] class is responsible for getting user's location
     * it needs a running Activity & FusedLocationProviderClient
     * */
    fun startLocation() {
        weatherInfoViewModel.locationHelper.initLocation(requireActivity(), fusedLocationClient)
    }
}