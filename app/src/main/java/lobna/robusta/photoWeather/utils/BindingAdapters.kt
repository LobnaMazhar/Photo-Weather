package lobna.robusta.photoWeather.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("recycler:adapter")
    fun recyclerAdapter(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    ) {
        recyclerView.adapter = adapter
    }
}
