package com.coffeetrip.data.repository

import com.coffeetrip.domain.model.Cafeteria
import com.coffeetrip.domain.model.CafeteriaDetalle
import com.coffeetrip.domain.model.EstadoCafeteria
import com.coffeetrip.domain.model.Horarios
import com.coffeetrip.domain.model.InformacionAdicional
import com.coffeetrip.domain.model.ProductoMenu
import com.coffeetrip.domain.model.Resena
import com.coffeetrip.domain.model.ServicioCafeteria
import com.coffeetrip.domain.model.Ubicacion

/**
 * Proveedor de datos mock para CafeteriaDetalle
 * En el futuro, reemplazar por fuente remota (Retrofit) y local (Room) según reglas del proyecto.
 */
object CafeteriaDataProvider {
    
    /**
     * Obtiene los detalles de una cafetería específica
     */
    fun getCafeteriaDetalle(cafeteria: Cafeteria): CafeteriaDetalle {
        val todasLasCafeterias = obtenerTodasLasCafeteriasConDetalles()
        return todasLasCafeterias.find { it.cafeteria.id == cafeteria.id }
            ?: CafeteriaDetalle(
                cafeteria = cafeteria,
                informacionAdicional = InformacionAdicional(
                    horarioTexto = "Horario no disponible"
                ),
                esFavorito = false
            )
    }
    
    /**
     * Obtiene todas las cafeterías con detalles completos
     */
    fun obtenerTodasLasCafeteriasConDetalles(): List<CafeteriaDetalle> {
        return listOf(
            CafeteriaDetalle(
                cafeteria = Cafeteria(
                    id = "1",
                    nombre = "El Rincón del Café",
                    descripcion = "Acogedor y perfecto para trabajar. Especialistas en café de especialidad y ambiente tranquilo.",
                    imagenUrl = "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400&h=200",
                    calificacion = 4.9,
                    distancia = 1.5,
                    estado = EstadoCafeteria.ABIERTO,
                    servicios = listOf(ServicioCafeteria.WIFI, ServicioCafeteria.PET_FRIENDLY),
                    ubicacion = Ubicacion(
                        latitud = 10.4850,
                        longitud = -66.8650,
                        direccion = "Av. Principal de Las Mercedes, Caracas"
                    )
                ),
                informacionAdicional = InformacionAdicional(
                    horarioTexto = "Lun-Dom: 7:00 AM - 8:00 PM"
                ),
                galeria = listOf(
                    "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400&h=200",
                    "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400&h=200",
                    "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=200",
                    "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=400&h=200",
                    "https://images.unsplash.com/photo-1521017432531-fbd92d768814?w=400&h=200",
                    "https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=400&h=200"
                ),
                resenas = listOf(
                    Resena(
                        id = "1",
                        usuario = "María González",
                        calificacion = 5.0,
                        comentario = "Excelente café y ambiente perfecto para trabajar. Los lattes son increíbles.",
                        fecha = "2 días"
                    ),
                    Resena(
                        id = "2",
                        usuario = "Carlos Rodríguez",
                        calificacion = 4.0,
                        comentario = "Buen lugar, el WiFi es rápido y tienen enchufes disponibles.",
                        fecha = "1 semana"
                    ),
                    Resena(
                        id = "3",
                        usuario = "Ana Martínez",
                        calificacion = 5.0,
                        comentario = "El mejor café de la zona, definitivamente volveré.",
                        fecha = "3 días"
                    )
                ),
                horarios = Horarios(
                    lunes = "7:00 AM - 8:00 PM",
                    martes = "7:00 AM - 8:00 PM",
                    miercoles = "7:00 AM - 8:00 PM",
                    jueves = "7:00 AM - 8:00 PM",
                    viernes = "7:00 AM - 9:00 PM",
                    sabado = "8:00 AM - 9:00 PM",
                    domingo = "8:00 AM - 8:00 PM"
                ),
                telefono = "+58 212 123-4567",
                menu = listOf(
                    ProductoMenu(
                        id = "1",
                        nombre = "Café Americano",
                        descripcion = "Café negro tradicional",
                        precio = 2.50,
                        categoria = com.coffeetrip.domain.model.CategoriaMenu.CAFE
                    ),
                    ProductoMenu(
                        id = "2",
                        nombre = "Latte",
                        descripcion = "Espresso con leche vaporizada",
                        precio = 3.50,
                        categoria = com.coffeetrip.domain.model.CategoriaMenu.CAFE
                    )
                ),
                esFavorito = false
            ),
            CafeteriaDetalle(
                cafeteria = Cafeteria(
                    id = "2",
                    nombre = "La Molienda",
                    descripcion = "Café de especialidad y postres exquisitos. Ambiente moderno con toques tradicionales.",
                    imagenUrl = "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=400&h=200",
                    calificacion = 4.7,
                    distancia = 1.1,
                    estado = EstadoCafeteria.CERRANDO_PRONTO,
                    servicios = listOf(ServicioCafeteria.WIFI, ServicioCafeteria.PET_FRIENDLY),
                    ubicacion = Ubicacion(
                        latitud = 10.4920,
                        longitud = -66.8620,
                        direccion = "Av. Francisco de Miranda, Altamira, Caracas"
                    )
                ),
                informacionAdicional = InformacionAdicional(
                    horarioTexto = "Lun-Dom: 8:00 AM - 7:00 PM"
                ),
                galeria = listOf(
                    "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=400&h=200",
                    "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400&h=200",
                    "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=200",
                    "https://images.unsplash.com/photo-1521017432531-fbd92d768814?w=400&h=200",
                    "https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=400&h=200",
                    "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400&h=200"
                ),
                resenas = listOf(
                    Resena(
                        id = "4",
                        usuario = "José Martínez",
                        calificacion = 5.0,
                        comentario = "Los postres son espectaculares. El cheesecake de café es mi favorito.",
                        fecha = "3 días"
                    ),
                    Resena(
                        id = "5",
                        usuario = "Laura Sánchez",
                        calificacion = 4.0,
                        comentario = "Buen café, ambiente agradable para estudiar.",
                        fecha = "5 días"
                    )
                ),
                horarios = Horarios(
                    lunes = "8:00 AM - 7:00 PM",
                    martes = "8:00 AM - 7:00 PM",
                    miercoles = "8:00 AM - 7:00 PM",
                    jueves = "8:00 AM - 7:00 PM",
                    viernes = "8:00 AM - 8:00 PM",
                    sabado = "9:00 AM - 8:00 PM",
                    domingo = "9:00 AM - 7:00 PM"
                ),
                telefono = "+58 212 987-6543",
                menu = listOf(
                    ProductoMenu(
                        id = "3",
                        nombre = "Cappuccino",
                        descripcion = "Espresso con leche espumosa",
                        precio = 3.00,
                        categoria = com.coffeetrip.domain.model.CategoriaMenu.CAFE
                    ),
                    ProductoMenu(
                        id = "4",
                        nombre = "Cheesecake de Café",
                        descripcion = "Postre especial de la casa",
                        precio = 4.50,
                        categoria = com.coffeetrip.domain.model.CategoriaMenu.POSTRES
                    )
                ),
                esFavorito = false
            ),
            CafeteriaDetalle(
                cafeteria = Cafeteria(
                    id = "3",
                    nombre = "Café Central",
                    descripcion = "Café tradicional en el corazón de la ciudad. Ambiente clásico y acogedor.",
                    imagenUrl = "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=200",
                    calificacion = 4.5,
                    distancia = 2.0,
                    estado = EstadoCafeteria.ABIERTO,
                    servicios = listOf(ServicioCafeteria.WIFI),
                    ubicacion = Ubicacion(
                        latitud = 10.4880,
                        longitud = -66.8700,
                        direccion = "Centro Comercial Ciudad Tamanaco, Chacao"
                    )
                ),
                informacionAdicional = InformacionAdicional(
                    horarioTexto = "Lun-Dom: 6:00 AM - 10:00 PM"
                ),
                galeria = listOf(
                    "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=200",
                    "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400&h=200",
                    "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=400&h=200",
                    "https://images.unsplash.com/photo-1521017432531-fbd92d768814?w=400&h=200",
                    "https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=400&h=200",
                    "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400&h=200"
                ),
                resenas = listOf(
                    Resena(
                        id = "6",
                        usuario = "Pedro López",
                        calificacion = 4.0,
                        comentario = "Café tradicional muy bueno, ambiente familiar.",
                        fecha = "1 semana"
                    )
                ),
                horarios = Horarios(
                    lunes = "6:00 AM - 10:00 PM",
                    martes = "6:00 AM - 10:00 PM",
                    miercoles = "6:00 AM - 10:00 PM",
                    jueves = "6:00 AM - 10:00 PM",
                    viernes = "6:00 AM - 11:00 PM",
                    sabado = "7:00 AM - 11:00 PM",
                    domingo = "7:00 AM - 10:00 PM"
                ),
                telefono = "+58 212 555-0123",
                menu = listOf(
                    ProductoMenu(
                        id = "5",
                        nombre = "Café Negro",
                        descripcion = "Café tradicional venezolano",
                        precio = 2.00,
                        categoria = com.coffeetrip.domain.model.CategoriaMenu.CAFE
                    )
                ),
                esFavorito = false
            ),
            CafeteriaDetalle(
                cafeteria = Cafeteria(
                    id = "4",
                    nombre = "Granos & Sabores",
                    descripcion = "Especialistas en café orgánico. Ambiente moderno y acogedor.",
                    imagenUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400&h=200",
                    calificacion = 4.8,
                    distancia = 0.8,
                    estado = EstadoCafeteria.ABIERTO,
                    servicios = listOf(ServicioCafeteria.WIFI, ServicioCafeteria.PET_FRIENDLY, ServicioCafeteria.TERRAZA),
                    ubicacion = Ubicacion(
                        latitud = 10.4800,
                        longitud = -66.8750,
                        direccion = "Av. Principal de La Castellana, Caracas"
                    )
                ),
                informacionAdicional = InformacionAdicional(
                    horarioTexto = "Lun-Dom: 7:30 AM - 9:00 PM"
                ),
                galeria = listOf(
                    "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400&h=200",
                    "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=200",
                    "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=400&h=200",
                    "https://images.unsplash.com/photo-1521017432531-fbd92d768814?w=400&h=200",
                    "https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=400&h=200",
                    "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400&h=200"
                ),
                resenas = listOf(
                    Resena(
                        id = "7",
                        usuario = "Carmen Ruiz",
                        calificacion = 5.0,
                        comentario = "Café orgánico excepcional, muy recomendado.",
                        fecha = "4 días"
                    ),
                    Resena(
                        id = "8",
                        usuario = "Diego Herrera",
                        calificacion = 4.0,
                        comentario = "Buen ambiente para trabajar, WiFi estable.",
                        fecha = "6 días"
                    )
                ),
                horarios = Horarios(
                    lunes = "7:30 AM - 9:00 PM",
                    martes = "7:30 AM - 9:00 PM",
                    miercoles = "7:30 AM - 9:00 PM",
                    jueves = "7:30 AM - 9:00 PM",
                    viernes = "7:30 AM - 10:00 PM",
                    sabado = "8:00 AM - 10:00 PM",
                    domingo = "8:00 AM - 9:00 PM"
                ),
                telefono = "+58 212 444-5678",
                menu = listOf(
                    ProductoMenu(
                        id = "6",
                        nombre = "Café Orgánico",
                        descripcion = "Café de granos orgánicos certificados",
                        precio = 3.00,
                        categoria = com.coffeetrip.domain.model.CategoriaMenu.CAFE
                    )
                ),
                esFavorito = false
            ),
            CafeteriaDetalle(
                cafeteria = Cafeteria(
                    id = "5",
                    nombre = "Espresso & Co",
                    descripcion = "Café gourmet con vista panorámica. Perfecto para reuniones.",
                    imagenUrl = "https://images.unsplash.com/photo-1521017432531-fbd92d768814?w=400&h=200",
                    calificacion = 4.6,
                    distancia = 1.8,
                    estado = EstadoCafeteria.CERRADO,
                    servicios = listOf(ServicioCafeteria.WIFI, ServicioCafeteria.ESTACIONAMIENTO),
                    ubicacion = Ubicacion(
                        latitud = 10.4970,
                        longitud = -66.8600,
                        direccion = "Av. Francisco Solano López, El Rosal"
                    )
                ),
                informacionAdicional = InformacionAdicional(
                    horarioTexto = "Lun-Dom: 8:00 AM - 6:00 PM"
                ),
                galeria = listOf(
                    "https://images.unsplash.com/photo-1521017432531-fbd92d768814?w=400&h=200",
                    "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400&h=200",
                    "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=200",
                    "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=400&h=200",
                    "https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=400&h=200",
                    "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400&h=200"
                ),
                resenas = listOf(
                    Resena(
                        id = "9",
                        usuario = "Roberto Silva",
                        calificacion = 4.0,
                        comentario = "Vista espectacular, café de calidad.",
                        fecha = "1 semana"
                    )
                ),
                horarios = Horarios(
                    lunes = "8:00 AM - 6:00 PM",
                    martes = "8:00 AM - 6:00 PM",
                    miercoles = "8:00 AM - 6:00 PM",
                    jueves = "8:00 AM - 6:00 PM",
                    viernes = "8:00 AM - 7:00 PM",
                    sabado = "9:00 AM - 7:00 PM",
                    domingo = "9:00 AM - 6:00 PM"
                ),
                telefono = "+58 212 333-9999",
                menu = listOf(
                    ProductoMenu(
                        id = "7",
                        nombre = "Espresso Gourmet",
                        descripcion = "Espresso premium de granos selectos",
                        precio = 4.00,
                        categoria = com.coffeetrip.domain.model.CategoriaMenu.CAFE
                    )
                ),
                esFavorito = false
            )
        )
    }
}