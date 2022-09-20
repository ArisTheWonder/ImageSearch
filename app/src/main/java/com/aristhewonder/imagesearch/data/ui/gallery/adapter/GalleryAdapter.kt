package com.aristhewonder.imagesearch.data.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aristhewonder.imagesearch.R
import com.aristhewonder.imagesearch.data.dto.Photo
import com.aristhewonder.imagesearch.databinding.GalleryPhotoItemLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import javax.inject.Inject

class GalleryAdapter @Inject constructor(
    diffUtilItemCallback: GalleryDiffUtilItemCallback
) : PagingDataAdapter<Photo, GalleryAdapter.GalleryViewHolder>(
    diffUtilItemCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = GalleryPhotoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        getItem(position)?.let { currentItem ->
            holder.bind(photo = currentItem)
        }
    }

    inner class GalleryViewHolder(private val binding: GalleryPhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.broken_image)
                    .into(imageView)

                textViewUserName.text = photo.user.username
            }
        }

    }

}