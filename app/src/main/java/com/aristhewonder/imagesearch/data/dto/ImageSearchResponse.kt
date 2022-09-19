package com.aristhewonder.imagesearch.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageSearchResponse(

	@field:SerializedName("total")
	val totalItems: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val photos: List<Photo>
): Parcelable