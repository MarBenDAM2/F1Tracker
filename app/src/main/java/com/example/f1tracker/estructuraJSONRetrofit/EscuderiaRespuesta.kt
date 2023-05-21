package com.example.f1tracker.estructuraJSONRetrofit

import com.google.gson.annotations.SerializedName

data class EscuderiaRespuesta(
    @SerializedName("MRData") val MRData: MRDataEscuderia
)
data class MRDataEscuderia(
    @SerializedName("ConstructorTable") val ConstructorTable: ConstructorTable
)
data class ConstructorTable(
    @SerializedName("Constructors") val equipo: List<Constructor>
)
data class Constructor(
    @SerializedName("constructorId") val idEscuderia: String,
    @SerializedName("url") val linkEscuderia: String,
    @SerializedName("name") val nombreEscuderia: String,
    @SerializedName("nationality") val nacionalidad: String
)