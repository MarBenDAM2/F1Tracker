import com.google.gson.annotations.SerializedName

data class PilotoRespuesta(
    @SerializedName("driverId") var idPiloto: String,
    @SerializedName("url") var linkPiloto: String,
    @SerializedName("givenName") var nomPiloto: String,
    @SerializedName("familyName") var apellidoPiloto: String,
    @SerializedName("dateOfBirth") var fechaNacimiento: String,
    @SerializedName("nationality") var nacionalidad: String
)