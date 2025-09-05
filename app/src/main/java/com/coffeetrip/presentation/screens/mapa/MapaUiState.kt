package com.coffeetrip.presentation.screens.mapa

import com.coffeetrip.domain.model.Cafeteria

/**
 * Estado de la UI para la pantalla del mapa
 */
data class MapaUiState(
    val isLoading: Boolean = false,
    val cafeterias: List<Cafeteria> = emptyList(),
    val cafeteriasFiltradas: List<Cafeteria> = emptyList(),
    val error: String? = null
) {
    /**
     * Indica si hay un error
     */
    val hasError: Boolean
        get() = error != null

    /**
     * Indica si la lista de cafeterías está vacía
     */
    val isEmpty: Boolean
        get() = cafeterias.isEmpty() && !isLoading

    /**
     * Indica si la lista de cafeterías filtradas está vacía
     */
    val isFilteredEmpty: Boolean
        get() = cafeteriasFiltradas.isEmpty() && !isLoading && cafeterias.isNotEmpty()
}
