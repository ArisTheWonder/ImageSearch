package com.aristhewonder.imagesearch.data.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aristhewonder.imagesearch.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: PhotoRepository
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val photos = currentQuery.switchMap { query ->
        repository.search(query).cachedIn(viewModelScope)
    }

    fun search(query: String) {
        currentQuery.value = query
    }
}