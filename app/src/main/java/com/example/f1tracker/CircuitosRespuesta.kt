package com.example.f1tracker

import com.google.gson.annotations.SerializedName

data class CircuitosRespuesta(
    @SerializedName("MRData") val MRData: MRDataCircuitos
)

data class MRDataCircuitos(
    @SerializedName("xmlns") val xmlns: String,
    @SerializedName("series") val series: String,
    @SerializedName("url") val url: String,
    @SerializedName("limit") val limit: String,
    @SerializedName("offset") val offset: String,
    @SerializedName("total") val total: String,
    @SerializedName("CircuitTable") val CircuitTable: CircuitTable
)

data class CircuitTable(
    @SerializedName("circuitId") val idCircuito: String,
    @SerializedName("Circuits") val Circuito: List<Circuits>
)

data class Circuits(
    @SerializedName("circuitId") val idCircuito: String,
    @SerializedName("url") val linkCircuito: String,
    @SerializedName("name") val nombreCircuito: String,
    @SerializedName("Location") val localizacion: Localizacion
)
data class Localizacion(
    @SerializedName("lat") val latitud: String,
    @SerializedName("long") val longitud: String,
    @SerializedName("locality") val localidad: String,
    @SerializedName("country") val pais: String
)