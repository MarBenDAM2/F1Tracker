package com.example.f1tracker.llamadasAPIRetrofit

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.f1tracker.InterfacesAPI.APIpiloto
import com.example.f1tracker.InterfacesApp.PilotosActivity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PilotosGETRetrofit : ViewModel() {
    var nom_piloto by mutableStateOf("")
    var idPiloto by mutableStateOf("")
    var num_perma by mutableStateOf<String?>("")
    var nomCortoPiloto by mutableStateOf<String?>("")
    var fecha_nacimiento by mutableStateOf("")
    var nacionalidad by mutableStateOf("")
    var linkFoto by mutableStateOf("")
    var encontrado by mutableStateOf(false)


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ergast.com/api/f1/drivers/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun busquedaPorNombre(busqueda: String){
        val controlExcepcion = CoroutineExceptionHandler { _, throwable ->
            println("Error, no hay conexion a internet")
        }
        CoroutineScope(Dispatchers.IO + controlExcepcion).launch {
            val llamada = getRetrofit().create(APIpiloto::class.java)
                .pilotoInformacion("${busqueda.replace("\\s+".toRegex(), "_")}.json")
            try {
                val piloto = llamada.body()
                if (llamada.isSuccessful) {
                    if (piloto != null) {
                        idPiloto = piloto.MRData.DriverTable.idPiloto
                        nom_piloto =
                            piloto.MRData.DriverTable.Piloto[0].nomPiloto + " " +
                                    piloto.MRData.DriverTable.Piloto[0].apellidoPiloto

                        when (piloto.MRData.DriverTable.Piloto[0].numPermanente) {
                            null -> {
                                num_perma = "N/D"
                            }

                            else -> {
                                num_perma = piloto.MRData.DriverTable.Piloto[0].numPermanente
                            }
                        }

                        when (piloto.MRData.DriverTable.Piloto[0].nomCortoPiloto) {
                            null -> {
                                nomCortoPiloto = "N/D"
                            }

                            else -> {
                                nomCortoPiloto = piloto.MRData.DriverTable.Piloto[0].nomCortoPiloto
                            }
                        }

                        fecha_nacimiento = piloto.MRData.DriverTable.Piloto[0].fechaNac
                        nacionalidad = piloto.MRData.DriverTable.Piloto[0].nacionalidad

                        pilotoLink()
                    }
                }
            } catch (IndexOutOfBoundsException: Exception) {
                print("Error")
            }
        }
    }


    fun pilotoLink() {

        val referencia = FirebaseStorage.getInstance().getReference("pilotos/${idPiloto}.jpg")

        CoroutineScope(Dispatchers.IO).launch {

            referencia.downloadUrl.addOnSuccessListener {

                linkFoto = it.toString()

            }.addOnFailureListener {

                linkFoto =
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Unknown_person.jpg/694px-Unknown_person.jpg"

            }

        }

        encontrado = true

    }


}