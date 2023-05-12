package com.example.f1tracker

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EscuderiaGETRetrofit : ViewModel() {
    var nom_escuderia : String by mutableStateOf("")
    var nacionalidad : String by mutableStateOf("")
    var urlEscuderia : String by mutableStateOf("")


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ergast.com/api/f1/constructors/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun busquedaPorNombre(busqueda:String){
        CoroutineScope(Dispatchers.IO).launch{
            val llamada = getRetrofit().create(APIEscuderias::class.java).escuderiaInformacion("${busqueda.replace("\\s+".toRegex(), "_")}.json")

            try{

                val escuderia = llamada.body()

                if (llamada.isSuccessful){
                    if(escuderia != null){
                        nom_escuderia = escuderia.MRData.ConstructorTable.equipo[0].nombreEscuderia
                        Log.d("nombre", nom_escuderia)
                        nacionalidad = escuderia.MRData.ConstructorTable.equipo[0].nacionalidad
                        urlEscuderia = escuderia.MRData.ConstructorTable.equipo[0].linkEscuderia
                    }
                }
            }catch (IndexOutOfBoundsException: Exception){
                println("No se ha encontrado la escudería")
            }
        }

    }

}