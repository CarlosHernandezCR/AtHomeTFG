package com.example.inhometfgandroidcarloshernandez.data.remote.apiServices

import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.example.inhometfgandroidcarloshernandez.common.ConstantesPaths
import com.example.inhometfgandroidcarloshernandez.data.model.request.CambiarEstadoRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.request.LoginRequestDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.GetUsuariosResponseDTO
import com.example.inhometfgandroidcarloshernandez.data.model.response.LoginResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UsuarioService {
    @POST(ConstantesPaths.LOGIN)
    suspend fun login(@Body loginDTO: LoginRequestDTO): Response<LoginResponseDTO>

    @POST(ConstantesPaths.CAMBIAR_ESTADO)
    suspend fun cambiarEstado(@Body cambiarEstadoRequestDTO: CambiarEstadoRequestDTO): Response<Void>

    @GET(ConstantesPaths.GET_USUARIOS)
    suspend fun getUsuarios(@Query(Constantes.IDCASA) id: Int): Response<GetUsuariosResponseDTO>
}