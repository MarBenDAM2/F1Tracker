package com.example.f1tracker.llamadasAPIRetrofit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.f1tracker.InterfacesAPI.APICalendario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class Carrera(var numCarrera: String, var nombreCarrera: String, var nombreCircuito: String, var fechaCarrera: String, var urlCarrera: String)

class CalendarioGETRetrofit : ViewModel() {
    var listaCarreras = SnapshotStateList<Carrera>()
    var terminado by mutableStateOf(false)


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ergast.com/api/f1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun busquedaPorYear(busqueda:String){
        CoroutineScope(Dispatchers.IO).launch{
            val llamada = getRetrofit().create(APICalendario::class.java).calendarioInformacion("${busqueda}.json")

            try{

                val calendario = llamada.body()

                if (llamada.isSuccessful){
                    if(calendario != null){
                        if (listaCarreras.isNotEmpty()){
                            listaCarreras.clear()
                        }
                        for (i in calendario.MRData.RaceTable.Carrera){
                            listaCarreras.add(Carrera(i.numCarrera, i.nombreCarrera, i.circuito.nombreCircuito, i.fechaCarrera, i.urlCarrera))
                        }
                    }
                    terminado = true
                }
            } catch (IndexOutOfBoundsException: Exception) {
                println("No se ha encontrado el año que buscas")
            }
        }

    }

}