package com.example.f1tracker.estructuraJSONRetrofit

import com.google.gson.annotations.SerializedName

data class CircuitosRespuesta(
    @SerializedName("MRData") val MRData: MRDataCircuitos
)
data class MRDataCircuitos(
    @SerializedName("CircuitTable") val CircuitTable: CircuitTable
)
data class CircuitTable(
    @SerializedName("Circuits") val Circuito: List<Circuits>
)
data class Circuits(
    @SerializedName("circuitId") val idCircuito: String,
    @SerializedName("url") val linkCircuito: String,
    @SerializedName("circuitName") val nombreCircuito: String,
    @SerializedName("Location") val localizacion: Localizacion
)
data class Localizacion(
    @SerializedName("lat") val latitud: String,
    @SerializedName("long") val longitud: String,
    @SerializedName("locality") val localidad: String,
    @SerializedName("country") val pais: String
)