package com.aristhewonder.imagesearch.data.datasource

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.aristhewonder.imagesearch.data.dto.Photo

interface ImageSearchDataSource {
    fun search(query: String): LiveData<PagingData<Photo>>
}