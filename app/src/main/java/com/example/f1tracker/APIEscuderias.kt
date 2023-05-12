package com.example.f1tracker

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIEscuderias {
    @GET
    suspend fun escuderiaInformacion(@Url url:String): Response<EscuderiaRespuesta>
}