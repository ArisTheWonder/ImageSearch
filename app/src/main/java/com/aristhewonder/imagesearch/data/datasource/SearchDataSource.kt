package com.aristhewonder.imagesearch.data.datasource

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.aristhewonder.imagesearch.data.dto.Photo

interface SearchDataSource {
    fun search(query: String): LiveData<PagingData<Photo>>
}