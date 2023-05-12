package com.example.f1tracker

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APICircuitos {
    @GET
    suspend fun circuitoInformacion(@Url url:String): Response<CircuitosRespuesta>
}