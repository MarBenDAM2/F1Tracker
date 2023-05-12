package com.example.f1tracker

import com.google.gson.annotations.SerializedName

data class EscuderiaRespuesta(
    @SerializedName("MRData") val MRData: MRDataEscuderia
)

data class MRDataEscuderia(
    @SerializedName("xmlns") val xmlns: String,
    @SerializedName("series") val series: String,
    @SerializedName("url") val url: String,
    @SerializedName("limit") val limit: String,
    @SerializedName("offset") val offset: String,
    @SerializedName("total") val total: String,
    @SerializedName("ConstructorTable") val ConstructorTable: ConstructorTable
)

data class ConstructorTable(
    @SerializedName("constructorId") val idEscuderia: String,
    @SerializedName("Constructors") val equipo: List<Constructor>
)

data class Constructor(
    @SerializedName("constructorId") val idEscuderia: String,
    @SerializedName("url") val linkEscuderia: String,
    @SerializedName("name") val nombreEscuderia: String,
    @SerializedName("nationality") val nacionalidad: String
)