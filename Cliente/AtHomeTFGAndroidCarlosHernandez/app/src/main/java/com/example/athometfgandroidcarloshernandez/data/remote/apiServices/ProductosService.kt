package com.example.athometfgandroidcarloshernandez.data.remote.apiServices

import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.athometfgandroidcarloshernandez.common.ConstantesPaths.CANTIDAD_QUERY
import com.example.athometfgandroidcarloshernandez.common.ConstantesPaths.IDCAJON_QUERY
import com.example.athometfgandroidcarloshernandez.common.ConstantesPaths.NOMBRE_QUERY
import com.example.athometfgandroidcarloshernandez.data.model.ProductoDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarCantidadProductoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.PedirPrestadoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.CargarProductosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.PedirPrestadoResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ProductosService {
    @POST(ConstantesPaths.CAMBIAR_CANTIDAD_PRODUCTO)
    suspend fun cambiarCantidad(@Body cambiarCantidadProductoRequestDTO: CambiarCantidadProductoRequestDTO): Response<Void>

    @GET(ConstantesPaths.CARGAR_PRODUCTOS)
    suspend fun cargarProductos(
        @Query(Constantes.IDCAJON) idCajon: String?,
        @Query(Constantes.IDMUEBLE) idMueble: String?
    ): Response<CargarProductosResponseDTO>

    @Multipart
    @POST(ConstantesPaths.AGREGAR_PRODUCTO)
    suspend fun agregarProducto(
        @Part(NOMBRE_QUERY) nombre: RequestBody,
        @Part(CANTIDAD_QUERY) cantidad: RequestBody,
        @Part imagen: MultipartBody.Part,
        @Part(IDCAJON_QUERY) idCajon: RequestBody,
    ): Response<ProductoDTO>

    @POST(ConstantesPaths.PEDIR_PRESTADO)
    suspend fun pedirPrestado(@Body pedirPrestadoRequestDTO: PedirPrestadoRequestDTO): Response<PedirPrestadoResponseDTO>
}