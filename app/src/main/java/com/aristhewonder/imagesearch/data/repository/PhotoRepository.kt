package com.aristhewonder.imagesearch.data.repository

import com.aristhewonder.imagesearch.data.datasource.SearchDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    private val dataSource: SearchDataSource
) {
    fun search(query: String) = dataSource.search(query)
}