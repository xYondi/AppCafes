package com.coffeetrip.data.repository

import com.coffeetrip.domain.repository.FavoritosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación del repositorio de favoritos
 * Mantiene el estado compartido de favoritos en memoria
 * En una app real, esto se persistiría en Room o DataStore
 */
@Singleton
class FavoritosRepositoryImpl @Inject constructor() : FavoritosRepository {
    
    private val _favoritosIds = MutableStateFlow(setOf<String>())
    
    override fun obtenerFavoritos(): Flow<Set<String>> = _favoritosIds.asStateFlow()
    
    override suspend fun agregarFavorito(cafeteriaId: String) {
        _favoritosIds.value = _favoritosIds.value + cafeteriaId
    }
    
    override suspend fun quitarFavorito(cafeteriaId: String) {
        _favoritosIds.value = _favoritosIds.value - cafeteriaId
    }
    
    override suspend fun toggleFavorito(cafeteriaId: String) {
        val favoritosActuales = _favoritosIds.value
        if (favoritosActuales.contains(cafeteriaId)) {
            quitarFavorito(cafeteriaId)
        } else {
            agregarFavorito(cafeteriaId)
        }
    }
    
    override suspend fun esFavorito(cafeteriaId: String): Boolean {
        return _favoritosIds.value.contains(cafeteriaId)
    }
}

