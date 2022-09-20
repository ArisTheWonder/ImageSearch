package com.aristhewonder.imagesearch.di

import com.aristhewonder.imagesearch.data.datasource.SearchDataSource
import com.aristhewonder.imagesearch.data.datasource.NetworkSearchDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ImageSearchDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkImageSearchDataSource(
        networkImageSearchDataSource: NetworkSearchDataSource
    ): SearchDataSource

}