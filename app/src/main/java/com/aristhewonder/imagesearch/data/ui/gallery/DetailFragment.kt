package com.aristhewonder.imagesearch.data.ui.gallery

import android.os.Bundle
import android.view.View
import com.aristhewonder.imagesearch.R
import com.aristhewonder.imagesearch.data.ui.BaseFragment
import com.aristhewonder.imagesearch.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getBinding(view: View): FragmentDetailBinding {
        return FragmentDetailBinding.bind(view)
    }

}