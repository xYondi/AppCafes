package com.coffeetrip.di

import com.coffeetrip.data.repository.CafeteriaRepositoryImpl
import com.coffeetrip.data.repository.FavoritosRepositoryImpl
import com.coffeetrip.domain.repository.CafeteriaRepository
import com.coffeetrip.domain.repository.FavoritosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindCafeteriaRepository(impl: CafeteriaRepositoryImpl): CafeteriaRepository
    
    @Binds
    @Singleton
    abstract fun bindFavoritosRepository(impl: FavoritosRepositoryImpl): FavoritosRepository
}


