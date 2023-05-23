package com.example.f1tracker.estructuraJSONRetrofit

import com.google.gson.annotations.SerializedName

data class PilotoRespuesta(
    @SerializedName("MRData") val MRData: MRData
)
data class MRData(
    @SerializedName("DriverTable") val DriverTable: DriverTable
)
data class DriverTable(
    @SerializedName("driverId") val idPiloto: String,
    @SerializedName("Drivers") val Piloto: List<Driver>
)
data class Driver(
    @SerializedName("permanentNumber") val numPermanente: String,
    @SerializedName("code") val nomCortoPiloto: String,
    @SerializedName("givenName") val nomPiloto: String,
    @SerializedName("familyName") val apellidoPiloto: String,
    @SerializedName("dateOfBirth") val fechaNac: String,
    @SerializedName("nationality") val nacionalidad: String
)