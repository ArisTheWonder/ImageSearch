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
        setupRecyclerView()

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

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            val onRetryClicked: () -> Unit = {
                galleryAdapter.retry()
            }
            adapter = galleryAdapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter(onRetryClicked),
                footer = PhotoLoadStateAdapter(onRetryClicked)
            )

            galleryAdapter.addLoadStateListener { loadState ->
                renderView()
            }

        }
    }

    private fun convertState(loadState: CombinedLoadStates): PhotoGalleryState {
        return when (loadState) {
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

    private fun renderView(loadState: CombinedLoadStates) {

        binding.apply {
            with(loadState.source.refresh) {
                val state =

                progressBar.isVisible = this is LoadState.Loading
                recyclerView.isVisible = this is LoadState.NotLoading
                buttonRetry.isVisible = this is LoadState.Error
                textViewError.isVisible = this is LoadState.Error

                if (this is LoadState.NotLoading && loadState.append.endOfPaginationReached && galleryAdapter.itemCount == 0) {

                }
            }
        }

    }

    sealed class PhotoGalleryState {
        object Loading: PhotoGalleryState()
        object Error:PhotoGalleryState()
        object Data:PhotoGalleryState()
        object Empty:PhotoGalleryState()
    }
}