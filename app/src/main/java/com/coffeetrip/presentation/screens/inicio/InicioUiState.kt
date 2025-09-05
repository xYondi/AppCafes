package com.coffeetrip.presentation.screens.inicio

import com.coffeetrip.domain.model.Cafeteria

/**
 * Estado de UI para la pantalla de Inicio usando una jerarquía sellada.
 */
sealed class InicioUiState {
    data object Loading : InicioUiState()
    data class Success(val cafeterias: List<Cafeteria>) : InicioUiState()
    data class Error(val message: String) : InicioUiState()
    data object Empty : InicioUiState()
}


