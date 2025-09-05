package com.coffeetrip.domain.model

/**
 * Modelo de dominio que representa una cafetería
 */
data class Cafeteria(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val imagenUrl: String,
    val calificacion: Double,
    val distancia: Double, // en kilómetros
    val estado: EstadoCafeteria,
    val servicios: List<ServicioCafeteria>,
    val ubicacion: Ubicacion
)

// Estado de UI removido del dominio (mantenido en presentation)

/**
 * Enum que representa el estado de una cafetería
 */
enum class EstadoCafeteria {
    ABIERTO,
    CERRANDO_PRONTO,
    CERRADO
}

/**
 * Enum que representa los servicios disponibles en una cafetería
 */
enum class ServicioCafeteria {
    WIFI,
    PET_FRIENDLY,
    ESTACIONAMIENTO,
    TERRAZA,
    DELIVERY
}

/**
 * Modelo que representa la ubicación de una cafetería
 */
data class Ubicacion(
    val latitud: Double,
    val longitud: Double,
    val direccion: String
)


