package com.aristhewonder.imagesearch.data.datasource

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.aristhewonder.imagesearch.data.ImageSearchPagingSource
import com.aristhewonder.imagesearch.data.dto.Photo
import com.aristhewonder.imagesearch.data.endpoint.SearchApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkSearchDataSource @Inject constructor(private val api: SearchApi) :
    SearchDataSource {

    override fun search(query: String): LiveData<PagingData<Photo>> = Pager(
        config = buildPagingConfig(),
        pagingSourceFactory = { ImageSearchPagingSource(api, query) }
    ).liveData

    private fun buildPagingConfig() = PagingConfig(
        pageSize = 20,
        maxSize = 100,
        enablePlaceholders = false
    )

}