package com.example.f1tracker.estructuraJSONRetrofit

import com.google.gson.annotations.SerializedName

data class CalendarioRespuesta(
    @SerializedName("MRData") val MRData: MRDataCalendario
)

data class MRDataCalendario(
    @SerializedName("RaceTable") val RaceTable: RaceTable
)

data class RaceTable(
    @SerializedName("Races") val Carrera: List<Carreras>
)

data class Carreras(
    @SerializedName("round") val numCarrera: String,
    @SerializedName("raceName") val nombreCarrera: String,
    @SerializedName("Circuit") val circuito: Circuitos,
    @SerializedName("date") val fechaCarrera: String,
    @SerializedName("url") val urlCarrera: String
)

data class Circuitos(
    @SerializedName("circuitName") val nombreCircuito: String,
)