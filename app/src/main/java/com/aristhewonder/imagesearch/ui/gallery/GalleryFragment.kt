package com.aristhewonder.imagesearch.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aristhewonder.imagesearch.R
import com.aristhewonder.imagesearch.databinding.FragmentGalleryBinding
import com.aristhewonder.imagesearch.ui.BaseFragment
import com.aristhewonder.imagesearch.ui.gallery.adapter.GalleryAdapter
import com.aristhewonder.imagesearch.ui.gallery.adapter.PhotoLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()
    private var gridGalleryLayout = false

    @Inject
    lateinit var galleryAdapter: GalleryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle(title = "Gallery")
        setupMenu()
        setupViews()

        viewModel.photos.observe(viewLifecycleOwner) { pagingData ->
            galleryAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }

    }

    override fun getBinding(view: View): FragmentGalleryBinding {
        return FragmentGalleryBinding.bind(view)
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
                if (menuItem.itemId == R.id.action_change_list_layout) {
                    gridGalleryLayout = !gridGalleryLayout
                    menuItem.setIcon(
                        if (gridGalleryLayout) R.drawable.ic_baseline_view_module_24 else R.drawable.ic_baseline_view_list_24
                    )

                    setupViews()
                }
                return true
            }

        }
        menuHos.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupViews() {
        binding.apply {
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = buildLayoutManager(isGrid = gridGalleryLayout)
                adapter = configureAdapter(onLoadStateChanged = { loadState ->
                    renderView(convertState(loadState))
                })
            }

            buttonRetry.setOnClickListener { galleryAdapter.retry() }
        }
    }

    private fun buildLayoutManager(isGrid: Boolean): RecyclerView.LayoutManager {
        return if (isGrid)
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        else
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

    }

    private fun configureAdapter(
        onLoadStateChanged: (loadState: CombinedLoadStates) -> Unit
    ): ConcatAdapter {
        val onRetryClicked: () -> Unit = { galleryAdapter.retry() }

        galleryAdapter.setOnItemClick { photo ->
            findNavController().navigate(
                GalleryFragmentDirections.actionGalleryFragmentToDetailFragment(
                    photo
                )
            )
        }
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