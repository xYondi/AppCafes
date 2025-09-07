package com.coffeetrip.presentation.screens.favoritos

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffeetrip.data.repository.CafeteriaDataProvider
import com.coffeetrip.domain.model.CafeteriaDetalle
import com.coffeetrip.domain.repository.FavoritosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de Favoritos
 * Maneja la lógica de favoritos y el estado de la UI
 */
@HiltViewModel
class FavoritosViewModel @Inject constructor(
    private val favoritosRepository: FavoritosRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(FavoritosUiState())
    val uiState: StateFlow<FavoritosUiState> = _uiState.asStateFlow()
    
    // Estado del bottom sheet
    private val _cafeteriaSeleccionada: MutableStateFlow<CafeteriaDetalle?> = MutableStateFlow(null)
    val cafeteriaSeleccionada: StateFlow<CafeteriaDetalle?> = _cafeteriaSeleccionada.asStateFlow()
    
    // Flujo de favoritos desde el repositorio compartido
    val favoritosIds = favoritosRepository.obtenerFavoritos().stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = emptySet()
    )
    
    init {
        cargarFavoritos()
    }
    
    /**
     * Carga las cafeterías favoritas
     */
    private fun cargarFavoritos() {
        viewModelScope.launch {
            combine(
                favoritosIds,
                kotlinx.coroutines.flow.flowOf(CafeteriaDataProvider.obtenerTodasLasCafeteriasConDetalles())
            ) { favoritosIds, todasLasCafeterias ->
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                try {
                    // Filtrar solo las que están en favoritos
                    val favoritos = todasLasCafeterias.filter { cafeteria ->
                        favoritosIds.contains(cafeteria.cafeteria.id)
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
            }.collect { }
        }
    }
    
    /**
     * Agrega una cafetería a favoritos
     */
    fun agregarFavorito(cafeteriaId: String) {
        viewModelScope.launch {
            favoritosRepository.agregarFavorito(cafeteriaId)
        }
    }
    
    /**
     * Quita una cafetería de favoritos
     */
    fun quitarFavorito(cafeteriaId: String) {
        viewModelScope.launch {
            favoritosRepository.quitarFavorito(cafeteriaId)
        }
    }
    
    /**
     * Verifica si una cafetería está en favoritos
     */
    fun esFavorito(cafeteriaId: String): Boolean {
        return favoritosIds.value.contains(cafeteriaId)
    }
    
    /**
     * Alterna el estado de favorito de una cafetería
     */
    fun toggleFavorito(cafeteriaId: String) {
        viewModelScope.launch {
            favoritosRepository.toggleFavorito(cafeteriaId)
        }
    }
    
    /**
     * Selecciona una cafetería para mostrar en el bottom sheet
     */
    fun seleccionarCafeteria(cafeteriaDetalle: CafeteriaDetalle) {
        _cafeteriaSeleccionada.value = cafeteriaDetalle
    }

    /**
     * Cierra el bottom sheet
     */
    fun cerrarBottomSheet() {
        _cafeteriaSeleccionada.value = null
    }

    /**
     * Alterna el estado de favorito de la cafetería seleccionada
     */
    fun toggleFavorito() {
        _cafeteriaSeleccionada.value?.let { cafeteriaDetalle ->
            val cafeteriaId = cafeteriaDetalle.cafeteria.id
            viewModelScope.launch {
                favoritosRepository.toggleFavorito(cafeteriaId)
                val nuevoEstado = favoritosRepository.esFavorito(cafeteriaId)
                _cafeteriaSeleccionada.value = cafeteriaDetalle.copy(esFavorito = nuevoEstado)
            }
        }
    }

    /**
     * Maneja la acción de llamar a la cafetería
     */
    fun llamarCafeteria() {
        // En una aplicación real, aquí se abriría la aplicación de teléfono
        // Por ahora, solo registramos la acción
    }

    /**
     * Maneja la acción de obtener direcciones
     * Abre Google Maps con la ubicación de la cafetería seleccionada
     */
    fun obtenerDirecciones() {
        val cafeteriaDetalle = _cafeteriaSeleccionada.value
        if (cafeteriaDetalle != null) {
            val latitud = cafeteriaDetalle.cafeteria.ubicacion.latitud
            val longitud = cafeteriaDetalle.cafeteria.ubicacion.longitud
            val nombre = cafeteriaDetalle.cafeteria.nombre
            
            // Crear URI para Google Maps
            val uri = Uri.parse("google.navigation:q=$latitud,$longitud&mode=d")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            
            try {
                // Intentar abrir Google Maps
                mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(mapIntent)
            } catch (e: Exception) {
                // Si Google Maps no está instalado, abrir con navegador web
                val webUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$latitud,$longitud&travelmode=driving")
                val webIntent = Intent(Intent.ACTION_VIEW, webUri)
                try {
                    webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(webIntent)
                } catch (e2: Exception) {
                    // Si no hay navegador, mostrar coordenadas
                    val fallbackUri = Uri.parse("geo:$latitud,$longitud?q=$latitud,$longitud($nombre)")
                    val fallbackIntent = Intent(Intent.ACTION_VIEW, fallbackUri)
                    fallbackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(fallbackIntent)
                }
            }
        }
    }

    /**
     * Maneja la acción de compartir cafetería
     */
    fun compartirCafeteria() {
        // En una aplicación real, aquí se abriría el sistema de compartir
        // Por ahora, solo registramos la acción
    }
}
