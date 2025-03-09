package com.example.athometfgandroidcarloshernandez.data.remote.apiServices

import com.example.athometfgandroidcarloshernandez.common.Constantes
import com.example.athometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.athometfgandroidcarloshernandez.data.model.request.CambiarEstadoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.CrearEstadoRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.LoginRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.request.RegistroRequestDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.AccessTokenResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.CambiarEstadoResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.GetUsuariosResponseDTO
import com.example.athometfgandroidcarloshernandez.data.model.response.LoginResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UsuarioService {
    @POST(ConstantesPaths.LOGIN)
    suspend fun login(@Body loginDTO: LoginRequestDTO): Response<LoginResponseDTO>

    @POST(ConstantesPaths.CAMBIAR_ESTADO)
    suspend fun cambiarEstado(@Body cambiarEstadoRequestDTO: CambiarEstadoRequestDTO): Response<CambiarEstadoResponseDTO>

    @GET(ConstantesPaths.GET_USUARIOS)
    suspend fun getUsuarios(@Query(Constantes.IDCASA) idCasa: String): Response<GetUsuariosResponseDTO>

    @POST(ConstantesPaths.REFRESH_TOKEN)
    suspend fun refreshToken(@Body refreshToken: String): Response<AccessTokenResponseDTO>

    @POST(ConstantesPaths.REGISTRO)
    suspend fun registro(@Body registroRequestDTO: RegistroRequestDTO) : Response<Void>

    @POST(ConstantesPaths.CREAR_ESTADO)
    suspend fun crearEstado(@Body crearEstadoRequestDTO: CrearEstadoRequestDTO) : Response<Void>
}