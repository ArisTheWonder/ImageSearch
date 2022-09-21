package com.aristhewonder.imagesearch.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    @field:SerializedName("profile_image")
	val profileImage: ProfileImage,

    @field:SerializedName("name")
	val name: String?,

    @field:SerializedName("twitter_username")
	val twitterUsername: String?,

    @field:SerializedName("last_name")
	val lastName: String?,

    @field:SerializedName("links")
	val links: Links,

    @field:SerializedName("id")
	val id: String?,

    @field:SerializedName("first_name")
	val firstName: String?,

    @field:SerializedName("instagram_username")
	val instagramUsername: String?,

    @field:SerializedName("portfolio_url")
	val portfolioUrl: String?,

    @field:SerializedName("username")
	val username: String?
): Parcelable