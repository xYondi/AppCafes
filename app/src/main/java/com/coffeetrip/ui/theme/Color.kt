package com.coffeetrip.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Paleta de colores exacta extraída de Figma
 * Basada en el diseño INICIO V2 (node-id: 3-7246)
 */
object CoffeeTripColors {
    // Colores principales del diseño - exactos de Figma
    val bgBase = Color(0xFFFFFFFF)        // Fondo principal blanco (fill_N5IJJS)
    val bgSurface = Color(0xFF42210B)     // Color café oscuro para tarjetas (fill_I2VFJH)
    val textPrimary = Color(0xFF000000)   // Texto negro principal (para fondo blanco)
    val textSecondary = Color(0xFFA0AEC0) // Texto gris secundario
    val inactiveText = Color(0xFF4B5563)  // Texto inactivo (stroke_XTKBFK)
    val accentBrown = Color(0xFF7B3F00)   // Marrón de acento
    val activeBrown = Color(0xFF42210B)   // Color café oscuro para activo (fill_V0D3OF)
    val navbarBg = Color(0xFFFFFFFF)      // Fondo blanco para navegación (fill_WPNTJB)
    val beige = Color(0xFFF5F5DC)         // Beige suave
    val accentBlue = Color(0xFF3B82F6)    // Azul de acento
    val darkGray = Color(0xFF374151)      // Gris oscuro
    val yellowStar = Color(0xFFFBBF24)    // Amarillo para estrellas
    
    // Colores específicos de Figma
    val pillBackground = Color(0x1A000000) // rgba(0, 0, 0, 0.1) - exacto de Figma
    val cardShadow = Color(0x40000000)     // rgba(0, 0, 0, 0.25) - effect_0JNTR1
    val frameShadow = Color(0x80000000)    // rgba(0, 0, 0, 0.5) - effect_LALO86
}

/**
 * Colores para estados de cafetería - exactos de Figma
 */
object EstadoColors {
    val abierto = Color(0xFFEDE8DE)       // Beige claro para "Abierto" (fill_CVS4JE)
    val cerrandoPronto = Color(0xFFEDE8DE) // Beige claro para "Cerrando" (fill_CVS4JE)
    val cerrado = Color(0xFFEDE8DE)       // Beige claro para "Cerrado"
}

/**
 * Colores adicionales para iconos y elementos específicos - exactos de Figma
 */
object IconColors {
    val mapIcon = Color(0xFFEDE8DE)       // Beige claro para el ícono del mapa (stroke_VVYG15)
    val serviceIcons = Color(0xFFA0AEC0)  // Gris claro para iconos de servicios (fill_TGR8VM)
    val statusText = Color(0xFF42210B)    // Marrón oscuro para texto de estado (fill_Y2MPL0)
    val wifiIcon = Color(0xFFFEF7FF)      // Blanco para ícono WiFi (fill_M3AUH4)
    val starIcon = Color(0xFFFACC15)      // Amarillo para estrella (fill_62OA6B)
    val descriptionText = Color(0xFFEDE8DE) // Beige claro para texto de descripción (fill_CVS4JE)
    val inactiveText = Color(0xFF4B5563)  // Gris para iconos inactivos (stroke_GFGDTZ)
}
