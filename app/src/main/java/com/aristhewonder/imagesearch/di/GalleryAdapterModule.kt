package com.aristhewonder.imagesearch.di

import com.aristhewonder.imagesearch.data.ui.gallery.adapter.GalleryAdapter
import com.aristhewonder.imagesearch.data.ui.gallery.adapter.GalleryDiffUtilItemCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GalleryAdapterModule {

    @Singleton
    @Provides
    fun provideGalleryAdapter(diffUtilItemCallback: GalleryDiffUtilItemCallback): GalleryAdapter {
        return GalleryAdapter(diffUtilItemCallback)
    }
}