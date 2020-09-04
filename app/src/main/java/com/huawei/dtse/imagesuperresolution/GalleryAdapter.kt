package com.huawei.dtse.imagesuperresolution

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class GalleryAdapter(
    private val images: Array<String>,
    val itemClickListener: (String) -> Unit
) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val imageView = holder.galleryImageView
        Picasso.get()
            .load(images[position])
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo_black)
            .into(imageView)
    }

    inner class GalleryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val galleryImageView: ImageView = itemView.findViewById(R.id.imageItemView)

        init {
            galleryImageView.setOnClickListener {
                itemClickListener.invoke(images[adapterPosition])
            }
        }
    }
}