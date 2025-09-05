package com.coffeetrip.domain.usecase

import com.coffeetrip.domain.model.Cafeteria
import com.coffeetrip.domain.repository.CafeteriaRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * Caso de uso para obtener cafeterías populares.
 */
class ObtenerCafeteriasPopularesUseCase @Inject constructor(
    private val cafeteriaRepository: CafeteriaRepository
) {
    operator fun invoke(): Flow<List<Cafeteria>> = cafeteriaRepository.obtenerCafeteriasPopulares()
}


