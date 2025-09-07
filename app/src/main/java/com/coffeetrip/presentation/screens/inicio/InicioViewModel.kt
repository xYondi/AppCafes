package com.coffeetrip.presentation.screens.inicio

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffeetrip.domain.usecase.ObtenerCafeteriasPopularesUseCase
import com.coffeetrip.domain.model.Cafeteria
import com.coffeetrip.domain.model.CafeteriaDetalle
import com.coffeetrip.data.repository.CafeteriaDataProvider
import com.coffeetrip.domain.repository.FavoritosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de inicio.
 * Orquesta el caso de uso y expone estado de UI.
 */
@HiltViewModel
class InicioViewModel @Inject constructor(
    private val obtenerCafeteriasPopularesUseCase: ObtenerCafeteriasPopularesUseCase,
    private val favoritosRepository: FavoritosRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState: MutableStateFlow<InicioUiState> = MutableStateFlow(InicioUiState.Loading)
    val uiState: StateFlow<InicioUiState> = _uiState.asStateFlow()

    // Estado del bottom sheet
    private val _cafeteriaSeleccionada: MutableStateFlow<CafeteriaDetalle?> = MutableStateFlow(null)
    val cafeteriaSeleccionada: StateFlow<CafeteriaDetalle?> = _cafeteriaSeleccionada.asStateFlow()
    
    // Flujo de favoritos desde el repositorio compartido
    val favoritosIds: StateFlow<Set<String>> = favoritosRepository.obtenerFavoritos().stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = emptySet()
    )

    init {
        cargarCafeteriasPopulares()
    }

    private fun cargarCafeteriasPopulares() {
        viewModelScope.launch {
            obtenerCafeteriasPopularesUseCase()
                .onStart { _uiState.value = InicioUiState.Loading }
                .catch { throwable ->
                    _uiState.value = InicioUiState.Error(throwable.message ?: "Error desconocido")
                }
                .collect { cafeterias ->
                    _uiState.value = if (cafeterias.isEmpty()) {
                        InicioUiState.Empty
                    } else {
                        InicioUiState.Success(cafeterias)
                    }
                }
        }
    }

    fun refrescarCafeterias() {
        cargarCafeteriasPopulares()
    }

    /**
     * Selecciona una cafetería para mostrar en el bottom sheet
     */
    fun seleccionarCafeteria(cafeteria: Cafeteria) {
        viewModelScope.launch {
            val esFavorito = favoritosRepository.esFavorito(cafeteria.id)
            val cafeteriaDetalle = CafeteriaDataProvider.getCafeteriaDetalle(cafeteria).copy(
                esFavorito = esFavorito
            )
            _cafeteriaSeleccionada.value = cafeteriaDetalle
        }
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
     * Alterna el estado de favorito de una cafetería por ID
     */
    fun toggleFavorito(cafeteriaId: String) {
        viewModelScope.launch {
            favoritosRepository.toggleFavorito(cafeteriaId)
            
            // Actualizar el bottom sheet si está abierto para esta cafetería
            _cafeteriaSeleccionada.value?.let { cafeteriaDetalle ->
                if (cafeteriaDetalle.cafeteria.id == cafeteriaId) {
                    val nuevoEstado = favoritosRepository.esFavorito(cafeteriaId)
                    _cafeteriaSeleccionada.value = cafeteriaDetalle.copy(esFavorito = nuevoEstado)
                }
            }
        }
    }
    
    /**
     * Verifica si una cafetería está en favoritos
     */
    fun esFavorito(cafeteriaId: String): Boolean {
        return favoritosIds.value.contains(cafeteriaId)
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
