package com.example.f1tracker

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIpiloto {
    @GET
    suspend fun pilotoInformacion(@Url url:String): Response<PilotoRespuesta>
}