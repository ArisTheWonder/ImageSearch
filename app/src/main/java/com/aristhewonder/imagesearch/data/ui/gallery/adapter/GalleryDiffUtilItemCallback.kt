package com.aristhewonder.imagesearch.data.ui.gallery.adapter

import androidx.recyclerview.widget.DiffUtil
import com.aristhewonder.imagesearch.data.dto.Photo
import javax.inject.Inject

class GalleryDiffUtilItemCallback @Inject constructor() : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem

}