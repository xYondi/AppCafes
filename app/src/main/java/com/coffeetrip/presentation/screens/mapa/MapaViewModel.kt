package com.coffeetrip.presentation.screens.mapa

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffeetrip.domain.model.Cafeteria
import com.coffeetrip.domain.usecase.ObtenerCafeteriasPopularesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla del mapa
 * Maneja el estado de la búsqueda, filtros y cafeterías en el mapa
 */
@HiltViewModel
class MapaViewModel @Inject constructor(
    private val obtenerCafeteriasPopularesUseCase: ObtenerCafeteriasPopularesUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapaUiState())
    val uiState: StateFlow<MapaUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilters = MutableStateFlow(setOf<String>())
    val selectedFilters: StateFlow<Set<String>> = _selectedFilters.asStateFlow()

    private val _cafeteriaSeleccionada = MutableStateFlow<com.coffeetrip.domain.model.CafeteriaDetalle?>(null)
    val cafeteriaSeleccionada: StateFlow<com.coffeetrip.domain.model.CafeteriaDetalle?> = _cafeteriaSeleccionada.asStateFlow()

    private val _currentZoom = MutableStateFlow(12.5f)
    val currentZoom: StateFlow<Float> = _currentZoom.asStateFlow()
    
    // Lista de IDs de cafeterías favoritas (simulando almacenamiento local)
    // En una app real, esto se compartiría entre ViewModels usando un Repository
    private val _favoritosIds = MutableStateFlow(setOf<String>())
    val favoritosIds: StateFlow<Set<String>> = _favoritosIds.asStateFlow()

    init {
        cargarCafeterias()
    }

    /**
     * Carga las cafeterías para mostrar en el mapa
     * Usa las mismas cafeterías que el InicioScreen
     */
    private fun cargarCafeterias() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                obtenerCafeteriasPopularesUseCase().collect { cafeterias ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        cafeterias = cafeterias,
                        error = null
                    )
                    filtrarCafeterias()
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    /**
     * Actualiza la consulta de búsqueda
     */
    fun actualizarBusqueda(query: String) {
        _searchQuery.value = query
        filtrarCafeterias()
    }

    /**
     * Toggle de un filtro (agregar o quitar)
     */
    fun toggleFiltro(filtro: String) {
        val filtrosActuales = _selectedFilters.value.toMutableSet()
        
        if (filtrosActuales.contains(filtro)) {
            filtrosActuales.remove(filtro)
        } else {
            filtrosActuales.add(filtro)
        }
        
        _selectedFilters.value = filtrosActuales
        filtrarCafeterias()
    }

    /**
     * Filtra las cafeterías basado en la búsqueda y filtros seleccionados
     */
    private fun filtrarCafeterias() {
        val cafeteriasOriginales = _uiState.value.cafeterias
        val query = _searchQuery.value.lowercase()
        val filtros = _selectedFilters.value

        val cafeteriasFiltradas = cafeteriasOriginales.filter { cafeteria ->
            // Filtro por búsqueda
            val coincideBusqueda = query.isEmpty() || 
                cafeteria.nombre.lowercase().contains(query) ||
                cafeteria.descripcion.lowercase().contains(query)

            // Filtro por filtros seleccionados
            val coincideFiltros = filtros.isEmpty() || filtros.any { filtro ->
                when (filtro) {
                    "Guardados" -> cafeteria.id in getCafeteriasGuardadas() // TODO: Implementar lógica de guardados
                    "1 km" -> cafeteria.distancia <= 1.0
                    "4.5+" -> cafeteria.calificacion >= 4.5
                    "Abierto" -> cafeteria.estado == com.coffeetrip.domain.model.EstadoCafeteria.ABIERTO
                    "Wi-Fi" -> cafeteria.servicios.contains(com.coffeetrip.domain.model.ServicioCafeteria.WIFI)
                    "Pet Friendly" -> cafeteria.servicios.contains(com.coffeetrip.domain.model.ServicioCafeteria.PET_FRIENDLY)
                    else -> true
                }
            }

            coincideBusqueda && coincideFiltros
        }

        _uiState.value = _uiState.value.copy(
            cafeteriasFiltradas = cafeteriasFiltradas
        )
    }

    /**
     * Obtiene las cafeterías guardadas (placeholder)
     * TODO: Implementar con el sistema de favoritos/guardados
     */
    private fun getCafeteriasGuardadas(): Set<String> {
        // Por ahora retorna algunas cafeterías como ejemplo
        return setOf("1", "3") // IDs de cafeterías guardadas
    }

    /**
     * Centra el mapa en la ubicación actual del usuario
     */
    fun centrarEnUbicacionActual() {
        // TODO: Implementar lógica para obtener ubicación actual
        // Por ahora no hace nada
    }

    /**
     * Refresca las cafeterías
     */
    fun refrescar() {
        cargarCafeterias()
    }

    /**
     * Selecciona una cafetería para mostrar en el bottom sheet
     */
    fun seleccionarCafeteria(cafeteria: com.coffeetrip.domain.model.Cafeteria) {
        viewModelScope.launch {
            // Por ahora creamos un CafeteriaDetalle básico
            // En una implementación real, esto vendría del repositorio
            val cafeteriaDetalle = com.coffeetrip.domain.model.CafeteriaDetalle(
                cafeteria = cafeteria,
                esFavorito = _favoritosIds.value.contains(cafeteria.id),
                galeria = listOf(
                    cafeteria.imagenUrl,
                    "https://picsum.photos/id/1084/400/200",
                    "https://picsum.photos/id/1085/400/200",
                    "https://picsum.photos/id/1086/400/200"
                ),
                resenas = listOf(
                    com.coffeetrip.domain.model.Resena(
                        id = "1",
                        usuario = "María González",
                        calificacion = 5.0,
                        comentario = "Excelente café y ambiente perfecto para trabajar. Los lattes son increíbles.",
                        fecha = "2 días"
                    ),
                    com.coffeetrip.domain.model.Resena(
                        id = "2",
                        usuario = "Carlos Rodríguez",
                        calificacion = 4.0,
                        comentario = "Buen lugar, el WiFi es rápido y tienen enchufes disponibles.",
                        fecha = "1 semana"
                    )
                ),
                informacionAdicional = com.coffeetrip.domain.model.InformacionAdicional(
                    horarioTexto = "Lun-Dom: 7:00 AM - 8:00 PM"
                ),
                telefono = "+58 212 123-4567"
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
     * Toggle del estado de favorito de la cafetería seleccionada
     */
    fun toggleFavorito() {
        _cafeteriaSeleccionada.value?.let { cafeteriaDetalle ->
            val cafeteriaId = cafeteriaDetalle.cafeteria.id
            val esFavorito = !cafeteriaDetalle.esFavorito
            
            if (esFavorito) {
                _favoritosIds.value = _favoritosIds.value + cafeteriaId
            } else {
                _favoritosIds.value = _favoritosIds.value - cafeteriaId
            }
            
            _cafeteriaSeleccionada.value = cafeteriaDetalle.copy(esFavorito = esFavorito)
        }
    }
    
    /**
     * Alterna el estado de favorito de una cafetería por ID
     */
    fun toggleFavorito(cafeteriaId: String) {
        val esFavorito = _favoritosIds.value.contains(cafeteriaId)
        
        if (esFavorito) {
            _favoritosIds.value = _favoritosIds.value - cafeteriaId
        } else {
            _favoritosIds.value = _favoritosIds.value + cafeteriaId
        }
        
        // Actualizar el bottom sheet si está abierto para esta cafetería
        _cafeteriaSeleccionada.value?.let { cafeteriaDetalle ->
            if (cafeteriaDetalle.cafeteria.id == cafeteriaId) {
                _cafeteriaSeleccionada.value = cafeteriaDetalle.copy(esFavorito = !esFavorito)
            }
        }
    }
    
    /**
     * Verifica si una cafetería está en favoritos
     */
    fun esFavorito(cafeteriaId: String): Boolean {
        return _favoritosIds.value.contains(cafeteriaId)
    }

    /**
     * Llama a la cafetería seleccionada
     */
    fun llamarCafeteria() {
        val actual = _cafeteriaSeleccionada.value
        if (actual?.telefono != null) {
            // TODO: Implementar llamada telefónica
        }
    }

    /**
     * Obtiene direcciones a la cafetería seleccionada
     * Abre Google Maps con la ubicación de la cafetería seleccionada
     */
    fun obtenerDirecciones() {
        val actual = _cafeteriaSeleccionada.value
        if (actual != null) {
            val latitud = actual.cafeteria.ubicacion.latitud
            val longitud = actual.cafeteria.ubicacion.longitud
            val nombre = actual.cafeteria.nombre
            
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
     * Comparte la cafetería seleccionada
     */
    fun compartirCafeteria() {
        val actual = _cafeteriaSeleccionada.value
        if (actual != null) {
            // TODO: Implementar compartir
        }
    }

    /**
     * Actualiza el nivel de zoom del mapa
     */
    fun actualizarZoom(zoom: Float) {
        _currentZoom.value = zoom
    }

    /**
     * Calcula cuántas cafeterías son visibles según el zoom actual
     * El contador solo aparece cuando el zoom está muy alejado (≤ 10f)
     */
    fun obtenerCafeteriasVisibles(): Int {
        val zoom = _currentZoom.value
        return when {
            zoom <= 10f -> _uiState.value.cafeteriasFiltradas.size // Muestra contador cuando está muy alejado
            else -> 0 // No muestra contador cuando está cerca
        }
    }
}
