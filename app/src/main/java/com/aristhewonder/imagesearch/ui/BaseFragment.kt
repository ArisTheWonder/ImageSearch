package com.aristhewonder.imagesearch.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.aristhewonder.imagesearch.util.extension.requireAppCompatActivity

abstract class BaseFragment<V : ViewBinding>(@LayoutRes layoutResID: Int) : Fragment(layoutResID) {

    protected lateinit var binding: V

    protected abstract fun getBinding(view: View): V

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getBinding(view)
    }

    protected fun setActionBarTitle(title: String) {
        requireAppCompatActivity().supportActionBar?.title = title
    }

}