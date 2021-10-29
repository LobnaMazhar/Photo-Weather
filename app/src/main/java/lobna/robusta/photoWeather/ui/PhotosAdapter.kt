package lobna.robusta.photoWeather.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lobna.robusta.photoWeather.databinding.ItemPhotoThumbnailBinding
import lobna.robusta.photoWeather.interfaces.PhotoInterface
import lobna.robusta.photoWeather.model.ImageModel

class PhotosAdapter(private val items: ArrayList<ImageModel>, val photoInterface: PhotoInterface) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val itemPhotosBinding: ItemPhotoThumbnailBinding =
            ItemPhotoThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosViewHolder(itemPhotosBinding)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class PhotosViewHolder(var itemPhotosBinding: ItemPhotoThumbnailBinding) :
        RecyclerView.ViewHolder(itemPhotosBinding.root) {

        fun bind(item: ImageModel) {
            itemPhotosBinding.image.run {
                setImageBitmap(item.image)
                setOnClickListener { photoInterface.openPhoto(item.image) }
            }
        }
    }
}