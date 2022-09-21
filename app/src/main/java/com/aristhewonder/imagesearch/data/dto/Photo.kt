package com.aristhewonder.imagesearch.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(

    @field:SerializedName("color")
	val color: String,

    @field:SerializedName("created_at")
	val createdAt: String,

    @field:SerializedName("description")
	val description: String?,

    @field:SerializedName("liked_by_user")
	val likedByUser: Boolean,

    @field:SerializedName("urls")
	val urls: Urls,

    @field:SerializedName("width")
	val width: Int,

    @field:SerializedName("blur_hash")
	val blurHash: String,

    @field:SerializedName("links")
	val links: Links,

    @field:SerializedName("id")
	val id: String,

    @field:SerializedName("user")
	val user: User,

    @field:SerializedName("height")
	val height: Int,

    @field:SerializedName("likes")
	val likes: Int
): Parcelable