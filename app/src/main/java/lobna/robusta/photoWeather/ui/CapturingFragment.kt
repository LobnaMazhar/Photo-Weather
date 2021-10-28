package lobna.robusta.photoWeather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import lobna.robusta.photoWeather.databinding.FragmentCapturingBinding
import lobna.robusta.photoWeather.utils.CameraHelper
import lobna.robusta.photoWeather.viewmodel.CapturingViewModel

/**
 * A simple [Fragment] subclass
 * used to capture a photo for the weather
 *
 * use the [startCamera] method to begin initializing camera to be able to capture image
 * */
class CapturingFragment : Fragment() {

    private lateinit var fragmentCapturingBinding: FragmentCapturingBinding
    private lateinit var cameraView: PreviewView

    private val capturingViewModel by viewModels<CapturingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        fragmentCapturingBinding = FragmentCapturingBinding.inflate(inflater)

        fragmentCapturingBinding.cvm = capturingViewModel

        cameraView = fragmentCapturingBinding.cameraView

        capturingViewModel.capturedImage.observe(viewLifecycleOwner, {
            findNavController().navigate(
                CapturingFragmentDirections.capturingToInfo(it.first, it.second)
            )
        })

        return fragmentCapturingBinding.root
    }

    /**
     * once view is created start initializing camera
     * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCamera()
    }

    /**
     * [CameraHelper] class is responsible for initializing camera
     * it needs a running Context, LifecycleOwner, and PreviewView of the camera
     * */
    fun startCamera() {
        capturingViewModel.cameraHelper.initCamera(
            requireActivity(), this@CapturingFragment, cameraView
        )
    }
}