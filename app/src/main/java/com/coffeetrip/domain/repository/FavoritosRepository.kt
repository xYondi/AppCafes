package com.coffeetrip.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repositorio para manejar el estado de favoritos
 * Comparte el estado entre todas las pantallas de la aplicación
 */
interface FavoritosRepository {
    
    /**
     * Obtiene el flujo de IDs de cafeterías favoritas
     */
    fun obtenerFavoritos(): Flow<Set<String>>
    
    /**
     * Agrega una cafetería a favoritos
     */
    suspend fun agregarFavorito(cafeteriaId: String)
    
    /**
     * Quita una cafetería de favoritos
     */
    suspend fun quitarFavorito(cafeteriaId: String)
    
    /**
     * Alterna el estado de favorito de una cafetería
     */
    suspend fun toggleFavorito(cafeteriaId: String)
    
    /**
     * Verifica si una cafetería está en favoritos
     */
    suspend fun esFavorito(cafeteriaId: String): Boolean
}

