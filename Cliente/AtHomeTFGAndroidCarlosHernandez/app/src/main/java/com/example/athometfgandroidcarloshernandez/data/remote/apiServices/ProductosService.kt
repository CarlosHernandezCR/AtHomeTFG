package com.example.athometfgandroidcarloshernandez.data.remote.apiServices

import com.example.athometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.CargarProductosRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.CargarProductosResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductosService {
    @POST(ConstantesPaths.CAMBIAR_CANTIDAD_PRODUCTO)
    suspend fun cambiarCantidad(@Body cambiarProductoRequestDTO: CambiarProductoRequestDTO): Response<Void>

    @GET(ConstantesPaths.CARGAR_PRODUCTOS)
    suspend fun cargarProductos(@Body cargarProductos: CargarProductosRequestDTO): Response<CargarProductosResponseDTO>
}