package com.aristhewonder.imagesearch.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links(

	@field:SerializedName("self")
	val self: String,

	@field:SerializedName("html")
	val html: String,

	@field:SerializedName("photos")
	val photos: String,

	@field:SerializedName("likes")
	val likes: String,

	@field:SerializedName("download")
	val download: String
): Parcelable