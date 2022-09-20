package com.aristhewonder.imagesearch.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aristhewonder.imagesearch.data.dto.Photo
import com.aristhewonder.imagesearch.data.endpoint.SearchApi
import retrofit2.HttpException
import java.io.IOException

class ImageSearchPagingSource(
    private val api: SearchApi,
    private val query: String
) : PagingSource<Int, Photo>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.search(
                query = query,
                page = page,
                size = params.loadSize
            )
            val photos = response.photos
            LoadResult.Page(
                data = photos,
                prevKey = getPreviousKey(currentPage = page),
                nextKey = getNextKey(currentPage = page, reachedToEndOfTheData = photos.isEmpty())
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun getPreviousKey(currentPage: Int): Int? =
        if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1

    private fun getNextKey(currentPage: Int, reachedToEndOfTheData: Boolean): Int? =
        if (reachedToEndOfTheData) null else currentPage + 1
}