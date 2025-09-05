package com.coffeetrip.domain.repository

import com.coffeetrip.domain.model.Cafeteria
import kotlinx.coroutines.flow.Flow

/**
 * Abstracción del acceso a datos de cafeterías.
 */
interface CafeteriaRepository {
    fun obtenerCafeteriasPopulares(): Flow<List<Cafeteria>>
}


