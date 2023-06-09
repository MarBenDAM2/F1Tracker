package com.example.f1tracker.llamadasAPIRetrofit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.f1tracker.InterfacesAPI.APIEscuderias
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EscuderiaGETRetrofit : ViewModel() {
    var nom_escuderia : String by mutableStateOf("")
    var idEscuderia : String by mutableStateOf("")
    var nacionalidad : String by mutableStateOf("")
    var urlEscuderia : String by mutableStateOf("")
    var linkFoto by mutableStateOf("")
    var encontrado by mutableStateOf(false)


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ergast.com/api/f1/constructors/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun busquedaPorNombre(busqueda:String){
        val controlExcepcion = CoroutineExceptionHandler { _, throwable ->
            println("Error, no hay conexion a internet")
        }
        CoroutineScope(Dispatchers.IO + controlExcepcion).launch{
            val llamada = getRetrofit().create(APIEscuderias::class.java).escuderiaInformacion("${busqueda.replace("\\s+".toRegex(), "_")}.json")

            try{

                val escuderia = llamada.body()

                if (llamada.isSuccessful){
                    if(escuderia != null){
                        idEscuderia = escuderia.MRData.ConstructorTable.equipo[0].idEscuderia
                        nom_escuderia = escuderia.MRData.ConstructorTable.equipo[0].nombreEscuderia
                        nacionalidad = escuderia.MRData.ConstructorTable.equipo[0].nacionalidad
                        urlEscuderia = escuderia.MRData.ConstructorTable.equipo[0].linkEscuderia
                        conseguirImagen()
                    }
                }
            } catch (IndexOutOfBoundsException: Exception) {
                print("Error")
            }
        }

    }

    fun conseguirImagen(){

        CoroutineScope(Dispatchers.IO).launch {
            var referencia =
                FirebaseStorage.getInstance().getReference("escuderias/${idEscuderia}.png")

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