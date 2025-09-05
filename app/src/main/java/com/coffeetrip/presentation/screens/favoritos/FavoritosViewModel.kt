package com.coffeetrip.presentation.screens.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffeetrip.data.repository.CafeteriaDataProvider
import com.coffeetrip.domain.model.CafeteriaDetalle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de Favoritos
 * Maneja la lógica de favoritos y el estado de la UI
 */
@HiltViewModel
class FavoritosViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(FavoritosUiState())
    val uiState: StateFlow<FavoritosUiState> = _uiState.asStateFlow()
    
    // Lista de IDs de cafeterías favoritas (simulando almacenamiento local)
    // En una app real, esto se compartiría entre ViewModels usando un Repository
    private val _favoritosIds = MutableStateFlow(setOf<String>())
    private val favoritosIds: StateFlow<Set<String>> = _favoritosIds.asStateFlow()
    
    init {
        cargarFavoritos()
    }
    
    /**
     * Carga las cafeterías favoritas
     */
    private fun cargarFavoritos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Obtener todas las cafeterías con detalles
                val todasLasCafeterias = CafeteriaDataProvider.obtenerTodasLasCafeteriasConDetalles()
                
                // Filtrar solo las que están en favoritos
                val favoritos = todasLasCafeterias.filter { cafeteria ->
                    _favoritosIds.value.contains(cafeteria.cafeteria.id)
                }
                
                _uiState.value = _uiState.value.copy(
                    favoritos = favoritos,
                    isLoading = false,
                    isEmpty = favoritos.isEmpty()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isEmpty = true
                )
            }
        }
    }
    
    /**
     * Agrega una cafetería a favoritos
     */
    fun agregarFavorito(cafeteriaId: String) {
        val nuevosFavoritos = _favoritosIds.value + cafeteriaId
        _favoritosIds.value = nuevosFavoritos
        cargarFavoritos()
    }
    
    /**
     * Quita una cafetería de favoritos
     */
    fun quitarFavorito(cafeteriaId: String) {
        val nuevosFavoritos = _favoritosIds.value - cafeteriaId
        _favoritosIds.value = nuevosFavoritos
        cargarFavoritos()
    }
    
    /**
     * Verifica si una cafetería está en favoritos
     */
    fun esFavorito(cafeteriaId: String): Boolean {
        return _favoritosIds.value.contains(cafeteriaId)
    }
    
    /**
     * Alterna el estado de favorito de una cafetería
     */
    fun toggleFavorito(cafeteriaId: String) {
        if (esFavorito(cafeteriaId)) {
            quitarFavorito(cafeteriaId)
        } else {
            agregarFavorito(cafeteriaId)
        }
    }
}
