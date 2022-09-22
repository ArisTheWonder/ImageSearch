package com.aristhewonder.imagesearch.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.requireAppCompatActivity(): AppCompatActivity {
    with(requireActivity()) {
        if (this is AppCompatActivity) {
            return this
        }

        throw IllegalStateException("${this.javaClass.name} is not subtype of AppCompatActivity.")
    }
}