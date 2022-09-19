package com.aristhewonder.imagesearch.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Urls(

    @field:SerializedName("small")
    val small: String,

    @field:SerializedName("thumb")
    val thumb: String,

    @field:SerializedName("raw")
    val raw: String,

    @field:SerializedName("regular")
    val regular: String,

    @field:SerializedName("full")
    val full: String
) : Parcelable