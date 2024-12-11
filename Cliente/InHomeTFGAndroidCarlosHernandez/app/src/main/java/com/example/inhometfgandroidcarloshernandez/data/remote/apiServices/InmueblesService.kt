package com.example.inhometfgandroidcarloshernandez.data.remote.apiServices

import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.model.request.AgregarCajonRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.AgregarHabitacionRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.AgregarMuebleRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.CrearEventoRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.PantallaInmueblesResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface InmueblesService {
    @GET(ConstantesPaths.INMUEBLES)
    suspend fun getDatosHabitaciones(@Query(Constantes.IDCASA) id: String, @Query(Constantes.HABITACION) mueble: String?, @Query(Constantes.MUEBLE) habitacion: String?): Response<PantallaInmueblesResponseDTO>
    @POST(ConstantesPaths.AGREGAR_HABITACION)
    suspend fun agregarHabitacion(@Body agregarHabitacionRequestDTO: AgregarHabitacionRequestDTO): Response<Void>
    @POST(ConstantesPaths.AGREGAR_MUEBLE)
    suspend fun agregarMueble(@Body agregarMuebleRequestDTO: AgregarMuebleRequestDTO): Response<Void>
    @POST(ConstantesPaths.AGREGAR_CAJON)
    suspend fun agregarCajon(@Body agregarCajonRequestDTO: AgregarCajonRequestDTO): Response<Void>

}