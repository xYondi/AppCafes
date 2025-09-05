package com.coffeetrip.domain.model

/**
 * Modelo extendido que incluye información detallada de una cafetería
 * para mostrar en el bottom sheet expandido
 */
data class CafeteriaDetalle(
    val cafeteria: Cafeteria,
    val informacionAdicional: InformacionAdicional,
    val galeria: List<String> = emptyList(),
    val resenas: List<Resena> = emptyList(),
    val horarios: Horarios? = null,
    val telefono: String? = null,
    val menu: List<ProductoMenu> = emptyList(),
    val esFavorito: Boolean = false
)

/**
 * Información adicional de la cafetería
 */
data class InformacionAdicional(
    val horarioTexto: String,
    val fechaApertura: String? = null,
    val especialidades: List<String> = emptyList(),
    val caracteristicas: List<String> = emptyList()
)

/**
 * Horarios de funcionamiento
 */
data class Horarios(
    val lunes: String,
    val martes: String,
    val miercoles: String,
    val jueves: String,
    val viernes: String,
    val sabado: String,
    val domingo: String
)

/**
 * Modelo para las reseñas
 */
data class Resena(
    val id: String,
    val usuario: String,
    val calificacion: Double,
    val comentario: String,
    val fecha: String,
    val avatarUrl: String? = null
)

/**
 * Modelo para productos del menú
 */
data class ProductoMenu(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: CategoriaMenu,
    val imagenUrl: String? = null,
    val disponible: Boolean = true
)

/**
 * Categorías del menú
 */
enum class CategoriaMenu {
    CAFE,
    BEBIDAS_CALIENTES,
    BEBIDAS_FRIAS,
    POSTRES,
    DESAYUNOS,
    SNACKS
}

