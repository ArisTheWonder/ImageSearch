package com.aristhewonder.imagesearch.data.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import com.aristhewonder.imagesearch.R
import com.aristhewonder.imagesearch.data.ui.gallery.adapter.GalleryAdapter
import com.aristhewonder.imagesearch.data.ui.gallery.adapter.PhotoLoadStateAdapter
import com.aristhewonder.imagesearch.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()
    private lateinit var binding: FragmentGalleryBinding

    @Inject
    lateinit var galleryAdapter: GalleryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBinding(view)
        setupMenu()
        setupViews()

        viewModel.photos.observe(viewLifecycleOwner) { pagingData ->
            galleryAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }

    }

    private fun setBinding(view: View) {
        binding = FragmentGalleryBinding.bind(view)
    }

    private fun setupMenu() {
        val menuHos: MenuHost = requireActivity()
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.gallery_menu, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem?.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let { searchQuery ->
                            binding.recyclerView.scrollToPosition(0)
                            viewModel.search(query = searchQuery)
                            searchView.clearFocus()
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

        }
        menuHos.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupViews() {
        binding.apply {

            recyclerView.apply {
                setHasFixedSize(true)
                adapter = configureAdapter(onLoadStateChanged = { loadState ->
                    renderView(convertState(loadState))
                })
            }

            buttonRetry.setOnClickListener { galleryAdapter.retry() }
        }
    }

    private fun configureAdapter(
        onLoadStateChanged: (loadState: CombinedLoadStates) -> Unit
    ): ConcatAdapter {
        val onRetryClicked: () -> Unit = { galleryAdapter.retry() }

        galleryAdapter.addLoadStateListener { loadState ->
            onLoadStateChanged.invoke(loadState)
        }

        return galleryAdapter.withLoadStateHeaderAndFooter(
            header = PhotoLoadStateAdapter(onRetryClicked),
            footer = PhotoLoadStateAdapter(onRetryClicked)
        )
    }

    private fun convertState(loadState: CombinedLoadStates): PhotoGalleryState {
        return when (loadState.source.refresh) {
            is LoadState.Loading -> PhotoGalleryState.Loading
            is LoadState.NotLoading -> {
                if (loadState.append.endOfPaginationReached && galleryAdapter.itemCount == 0)
                    PhotoGalleryState.Empty
                else
                    PhotoGalleryState.Data
            }
            is LoadState.Error -> PhotoGalleryState.Error
        }
    }

    private fun renderView(state: PhotoGalleryState) {
        binding.apply {
            with(state) {
                progressBar.isVisible = this is PhotoGalleryState.Loading
                recyclerView.isVisible = this is PhotoGalleryState.Data
                buttonRetry.isVisible = this is PhotoGalleryState.Error
                textViewError.isVisible = this is PhotoGalleryState.Error
                textViewEmpty.isVisible = this is PhotoGalleryState.Empty
            }
        }
    }

    sealed class PhotoGalleryState {
        object Loading : PhotoGalleryState()
        object Error : PhotoGalleryState()
        object Data : PhotoGalleryState()
        object Empty : PhotoGalleryState()
    }
}