package com.example.f1tracker.llamadasAPIRetrofit

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.f1tracker.InterfacesAPI.APICircuitos
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CircuitosGETRetrofit : ViewModel() {
    var nom_circuito by mutableStateOf("")
    var linkCircuito by mutableStateOf("")
    var id_Circuito by mutableStateOf("")
    var latitudCircuito by mutableStateOf("")
    var longitudCircuito by mutableStateOf("")
    var localidadCircuito by mutableStateOf("")
    var paisCircuito by mutableStateOf("")
    var linkFoto by mutableStateOf("")
    var encontrado by mutableStateOf(false)


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ergast.com/api/f1/circuits/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun busquedaPorNombre(busqueda:String){
        val controlExcepcion = CoroutineExceptionHandler { _, throwable ->
            println("Error, no hay conexion a internet")
        }
        CoroutineScope(Dispatchers.IO + controlExcepcion).launch{
            val llamada = getRetrofit().create(APICircuitos::class.java).circuitoInformacion("${busqueda.replace("\\s+".toRegex(), "_")}.json")

            try{

                val circuito = llamada.body()

                if (llamada.isSuccessful){
                    if(circuito != null){
                        id_Circuito = circuito.MRData.CircuitTable.Circuito[0].idCircuito
                        nom_circuito = circuito.MRData.CircuitTable.Circuito[0].nombreCircuito
                        linkCircuito = circuito.MRData.CircuitTable.Circuito[0].linkCircuito
                        latitudCircuito = circuito.MRData.CircuitTable.Circuito[0].localizacion.latitud
                        longitudCircuito = circuito.MRData.CircuitTable.Circuito[0].localizacion.longitud
                        localidadCircuito = circuito.MRData.CircuitTable.Circuito[0].localizacion.localidad
                        paisCircuito = circuito.MRData.CircuitTable.Circuito[0].localizacion.pais
                        conseguirImagen()
                    }
                }
            } catch (IndexOutOfBoundsException: Exception) {
                println("No se ha encontrado el circuito que buscas")
            }
        }

    }
    fun conseguirImagen(){

        CoroutineScope(Dispatchers.IO).launch{

            val referencia = FirebaseStorage.getInstance().getReference("circuitos/${id_Circuito}.jpg")

            referencia.downloadUrl.addOnSuccessListener {
                linkFoto = it.toString()
            }.addOnFailureListener {
                linkFoto = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Unknown_person.jpg/694px-Unknown_person.jpg"
            }

        }

        encontrado = true
    }

}