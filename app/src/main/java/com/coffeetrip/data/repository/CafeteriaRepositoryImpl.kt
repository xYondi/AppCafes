package com.coffeetrip.data.repository

import com.coffeetrip.domain.model.Cafeteria
import com.coffeetrip.domain.model.EstadoCafeteria
import com.coffeetrip.domain.model.ServicioCafeteria
import com.coffeetrip.domain.model.Ubicacion
import com.coffeetrip.domain.repository.CafeteriaRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Implementación temporal del repositorio que retorna datos mock.
 * En el futuro, reemplazar por fuente remota (Retrofit) y local (Room) según reglas del proyecto.
 */
class CafeteriaRepositoryImpl @Inject constructor() : CafeteriaRepository {
    override fun obtenerCafeteriasPopulares(): Flow<List<Cafeteria>> = flow {
        val cafeterias: List<Cafeteria> = listOf(
            Cafeteria(
                id = "1",
                nombre = "El Rincón del Café",
                descripcion = "Acogedor y perfecto para trabajar. A solo 1.5 km.",
                imagenUrl = "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400&h=200&fit=crop",
                calificacion = 4.9,
                distancia = 1.5,
                estado = EstadoCafeteria.ABIERTO,
                servicios = listOf(ServicioCafeteria.WIFI, ServicioCafeteria.PET_FRIENDLY),
                ubicacion = Ubicacion(
                    latitud = 10.4850, // Las Mercedes, Caracas - corregido
                    longitud = -66.8650,
                    direccion = "Av. Principal de Las Mercedes, Caracas"
                )
            ),
            Cafeteria(
                id = "2",
                nombre = "La Molienda",
                descripcion = "Café de especialidad y postres exquisitos. A solo 1.1 km.",
                imagenUrl = "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=400&h=200&fit=crop",
                calificacion = 4.7,
                distancia = 1.1,
                estado = EstadoCafeteria.CERRANDO_PRONTO,
                servicios = listOf(ServicioCafeteria.WIFI, ServicioCafeteria.PET_FRIENDLY),
                ubicacion = Ubicacion(
                    latitud = 10.4920, // Altamira, Caracas - corregido
                    longitud = -66.8620,
                    direccion = "Av. Francisco de Miranda, Altamira, Caracas"
                )
            ),
            Cafeteria(
                id = "3",
                nombre = "Café Central",
                descripcion = "Café tradicional en el corazón de la ciudad. Ambiente clásico.",
                imagenUrl = "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=200&fit=crop",
                calificacion = 4.5,
                distancia = 2.0,
                estado = EstadoCafeteria.ABIERTO,
                servicios = listOf(ServicioCafeteria.WIFI),
                ubicacion = Ubicacion(
                    latitud = 10.4880, // Chacao, Caracas - corregido
                    longitud = -66.8700,
                    direccion = "Centro Comercial Ciudad Tamanaco, Chacao"
                )
            ),
            Cafeteria(
                id = "4",
                nombre = "Granos & Sabores",
                descripcion = "Especialistas en café orgánico. Ambiente moderno y acogedor.",
                imagenUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400&h=200&fit=crop",
                calificacion = 4.8,
                distancia = 0.8,
                estado = EstadoCafeteria.ABIERTO,
                servicios = listOf(ServicioCafeteria.WIFI, ServicioCafeteria.PET_FRIENDLY, ServicioCafeteria.TERRAZA),
                ubicacion = Ubicacion(
                    latitud = 10.4800, // La Castellana, Caracas - corregido
                    longitud = -66.8750,
                    direccion = "Av. Principal de La Castellana, Caracas"
                )
            ),
            Cafeteria(
                id = "5",
                nombre = "Espresso & Co",
                descripcion = "Café gourmet con vista panorámica. Perfecto para reuniones.",
                imagenUrl = "https://images.unsplash.com/photo-1521017432531-fbd92d768814?w=400&h=200&fit=crop",
                calificacion = 4.6,
                distancia = 1.8,
                estado = EstadoCafeteria.CERRADO,
                servicios = listOf(ServicioCafeteria.WIFI, ServicioCafeteria.ESTACIONAMIENTO),
                ubicacion = Ubicacion(
                    latitud = 10.4970, // El Rosal, Caracas - corregido
                    longitud = -66.8600,
                    direccion = "Av. Francisco Solano López, El Rosal"
                )
            )
        )
        emit(cafeterias)
    }
}


