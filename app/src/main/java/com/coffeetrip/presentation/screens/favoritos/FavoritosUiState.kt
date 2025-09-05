package com.coffeetrip.presentation.screens.favoritos

import com.coffeetrip.domain.model.CafeteriaDetalle

/**
 * Estado de la UI para la pantalla de Favoritos
 */
data class FavoritosUiState(
    val favoritos: List<CafeteriaDetalle> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = true
)
