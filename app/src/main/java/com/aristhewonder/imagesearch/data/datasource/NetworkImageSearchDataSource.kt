package com.aristhewonder.imagesearch.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.aristhewonder.imagesearch.data.ImageSearchPagingSource
import com.aristhewonder.imagesearch.data.endpoint.ImageSearchApi
import javax.inject.Inject

class NetworkImageSearchDataSource @Inject constructor(private val api: ImageSearchApi) :
    ImageSearchDataSource {

    override fun search(query: String) = Pager(
        config = buildPagingConfig(),
        pagingSourceFactory = { ImageSearchPagingSource(api, query) }
    ).liveData

    private fun buildPagingConfig() = PagingConfig(
        pageSize = 20,
        maxSize = 100,
        enablePlaceholders = false
    )

}