package com.example.athometfgandroidcarloshernandez.data.remote.apiServices

import com.example.athometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarEstadoRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ProductosService {
    @POST(ConstantesPaths.CAMBIAR_CANTIDAD_PRODUCTO)
    suspend fun cambiarCantidad(@Body cambiarCambiarEstadoRequestDTO: CambiarEstadoRequestDTO): Response<Void>
}