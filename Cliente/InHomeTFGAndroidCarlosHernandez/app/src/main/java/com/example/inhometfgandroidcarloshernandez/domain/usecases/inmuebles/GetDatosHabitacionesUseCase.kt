package com.example.inhometfgandroidcarloshernandez.domain.usecases.inmuebles

import com.example.inhometfgandroidcarloshernandez.data.repositories.InmueblesRepository
import javax.inject.Inject

class GetDatosHabitacionesUseCase @Inject constructor(
    private val inmueblesRepository: InmueblesRepository
){
    operator fun invoke(id:String,mueble:String?,habitacion:String?) = inmueblesRepository.getDatosHabitaciones(id,mueble,habitacion)
}