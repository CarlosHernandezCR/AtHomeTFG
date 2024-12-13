package com.example.athometfgandroidcarloshernandez.domain.usecases.inmuebles

import com.example.athometfgandroidcarloshernandez.data.repositories.InmueblesRepository
import javax.inject.Inject

class AgregarHabitacionUseCase  @Inject constructor(
    private val inmueblesRepository: InmueblesRepository
) {
    operator fun invoke(idCasa:String,habitacion: String) = inmueblesRepository.agregarHabitacion(idCasa,habitacion)
}