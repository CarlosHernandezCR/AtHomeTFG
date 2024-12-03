package com.example.inhometfgandroidcarloshernandez.domain.usecases.inmuebles

import com.example.inhometfgandroidcarloshernandez.data.repositories.InmueblesRepository
import javax.inject.Inject

class GetDatosHabitacionesUseCase @Inject constructor(
    private val inmueblesRepository: InmueblesRepository
){
    operator fun invoke(id:Int) = inmueblesRepository.getDatosHabitaciones(id)
}