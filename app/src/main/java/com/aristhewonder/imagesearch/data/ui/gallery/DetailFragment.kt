package com.aristhewonder.imagesearch.data.ui.gallery

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.aristhewonder.imagesearch.R
import com.aristhewonder.imagesearch.data.ui.BaseFragment
import com.aristhewonder.imagesearch.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photo = args.photo
        setActionBarTitle(title = "Detail")
        binding.apply {
            Glide.with(this@DetailFragment)
                .load(photo.urls.full)
                .error(R.drawable.broken_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewDescription.text = photo.description
                        textViewCreator.text = photo.user.username
                        return false
                    }

                })
                .into(imageView)
        }
    }

    override fun getBinding(view: View): FragmentDetailBinding {
        return FragmentDetailBinding.bind(view)
    }

}