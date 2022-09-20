package com.aristhewonder.imagesearch.data.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        setupRecyclerView()

        viewModel.photos.observe(viewLifecycleOwner) { pagingData ->
            galleryAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }

    }

    private fun setBinding(view: View) {
        binding = FragmentGalleryBinding.bind(view)
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

        }
    }
}