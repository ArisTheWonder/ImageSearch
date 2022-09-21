package com.aristhewonder.imagesearch.data.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<V : ViewBinding>(@LayoutRes layoutResID: Int) : Fragment(layoutResID) {

    protected lateinit var binding: V

    protected abstract fun getBinding(view: View): V

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getBinding(view)
    }

}