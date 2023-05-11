package com.example.f1tracker

import com.google.gson.annotations.SerializedName

data class PilotoRespuesta(
    @SerializedName("MRData") val MRData: MRData
)

data class MRData(
    @SerializedName("xmlns") val xmlns: String,
    @SerializedName("series") val series: String,
    @SerializedName("url") val url: String,
    @SerializedName("limit") val limit: String,
    @SerializedName("offset") val offset: String,
    @SerializedName("total") val total: String,
    @SerializedName("DriverTable") val DriverTable: DriverTable
)

data class DriverTable(
    @SerializedName("driverId") val idPiloto: String,
    @SerializedName("Drivers") val Piloto: List<Driver>
)

data class Driver(
    @SerializedName("driverId") val idPiloto: String,
    @SerializedName("permanentNumber") val numPermanente: String,
    @SerializedName("code") val nomCortoPiloto: String,
    @SerializedName("url") val linkPiloto: String,
    @SerializedName("givenName") val nomPiloto: String,
    @SerializedName("familyName") val apellidoPiloto: String,
    @SerializedName("dateOfBirth") val fechaNac: String,
    @SerializedName("nationality") val nacionalidad: String
)