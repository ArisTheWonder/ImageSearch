package com.aristhewonder.imagesearch.data.endpoint

import com.aristhewonder.imagesearch.data.ApiPath
import com.aristhewonder.imagesearch.data.dto.ImageSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageSearchApi {

    @GET(ApiPath.SEARCH)
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") size: Int,
    ): ImageSearchResponse
}