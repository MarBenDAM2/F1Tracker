package com.example.f1tracker.InterfacesAPI

import com.example.f1tracker.estructuraJSONRetrofit.CalendarioRespuesta
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APICalendario {
    @GET
    suspend fun calendarioInformacion(@Url url:String): Response<CalendarioRespuesta>
}