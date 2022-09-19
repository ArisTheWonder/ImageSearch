package com.aristhewonder.imagesearch.data.repository

import com.aristhewonder.imagesearch.data.datasource.ImageSearchDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageSearchRepository @Inject constructor(
    private val dataSource: ImageSearchDataSource
) {
    fun search(query: String) = dataSource.search(query)
}