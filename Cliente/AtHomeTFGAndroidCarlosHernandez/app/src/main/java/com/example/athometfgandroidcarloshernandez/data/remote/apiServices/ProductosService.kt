package com.example.athometfgandroidcarloshernandez.data.remote.apiServices

import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.athometfgandroidcarloshernandez.data.model.ProductoDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.AgregarProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarCantidadProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.CargarProductosResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductosService {
    @POST(ConstantesPaths.CAMBIAR_CANTIDAD_PRODUCTO)
    suspend fun cambiarCantidad(@Body cambiarCantidadProductoRequestDTO: CambiarCantidadProductoRequestDTO): Response<Void>

    @GET(ConstantesPaths.CARGAR_PRODUCTOS)
    suspend fun cargarProductos(@Query(Constantes.IDCAJON) idCajon:String?,@Query(Constantes.IDMUEBLE) idMueble:String?): Response<CargarProductosResponseDTO>

    @POST(ConstantesPaths.AGREGAR_PRODUCTO)
    suspend fun agregarProducto(@Body agregarProductoRequestDTO: AgregarProductoRequestDTO): Response<ProductoDTO>
}