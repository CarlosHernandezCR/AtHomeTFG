package com.example.athometfgandroidcarloshernandez.data.remote.datasource

import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarCajonRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarHabitacionRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarMuebleRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.PantallaInmueblesResponseDTO
import com.example.athometfgandroidcarloshernandez.data.remote.apiServices.InmueblesService
import com.example.athometfgandroidcarloshernandez.data.remote.util.NetworkResult
import javax.inject.Inject

class InmueblesRemoteDataSource @Inject constructor(
    private val inmueblesService: InmueblesService
) :BaseApiResponse() {
    suspend fun getDatosHabitaciones(id:String,mueble:String?, habitacion:String?): NetworkResult<PantallaInmueblesResponseDTO> =
        safeApiCall{ inmueblesService.getDatosHabitaciones(id,mueble,habitacion) }
    suspend fun agregarHabitacion(habitacion: AgregarHabitacionRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody{ inmueblesService.agregarHabitacion(habitacion) }
    suspend fun agregarMueble(mueble: AgregarMuebleRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody{ inmueblesService.agregarMueble(mueble) }
    suspend fun agregarCajon(cajon:AgregarCajonRequestDTO): NetworkResult<Boolean> =
        safeApiCallNoBody{ inmueblesService.agregarCajon(cajon) }
}