package com.example.f1tracker.InterfacesAPI

import com.example.f1tracker.estructuraJSONRetrofit.CircuitosRespuesta
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APICircuitos {
    @GET
    suspend fun circuitoInformacion(@Url url:String): Response<CircuitosRespuesta>
}