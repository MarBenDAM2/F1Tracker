package com.example.f1tracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PilotosViewModel : ViewModel() {
    var nom_piloto by mutableStateOf("")
    var num_perma by mutableStateOf<String?>("")
    var nomCortoPiloto by mutableStateOf<String?>("")
    var fecha_nacimiento by mutableStateOf("")
    var nacionalidad by mutableStateOf("")

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ergast.com/api/f1/drivers/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun busquedaPorNombre(busqueda:String){
        CoroutineScope(Dispatchers.IO).launch {
            val llamada = getRetrofit().create(APIpiloto::class.java).pilotoInformacion("${busqueda.replace("\\s+".toRegex(),"_")}.json")
            try {
                val piloto = llamada.body()
                if (llamada.isSuccessful){
                    if (piloto != null) {
                        nom_piloto = piloto.MRData.DriverTable.Piloto[0].nomPiloto + " " + piloto.MRData.DriverTable.Piloto[0].apellidoPiloto

                        when (piloto.MRData.DriverTable.Piloto[0].numPermanente) {
                            null -> {
                                num_perma = "N/D"
                            }
                            else -> {
                                num_perma = piloto.MRData.DriverTable.Piloto[0].numPermanente
                            }
                        }

                        when (piloto.MRData.DriverTable.Piloto[0].nomCortoPiloto){
                            null -> {
                                nomCortoPiloto = "N/D"
                            }
                            else -> {
                                nomCortoPiloto = piloto.MRData.DriverTable.Piloto[0].nomCortoPiloto
                            }
                        }

                        fecha_nacimiento = piloto.MRData.DriverTable.Piloto[0].fechaNac
                        nacionalidad = piloto.MRData.DriverTable.Piloto[0].nacionalidad
                    }
                }
            } catch (IndexOutOfBoundsException: Exception){
                println("No se encontró el piloto")
            }
        }
    }


    fun pilotoLink(): String {

        return when (nom_piloto){
            "Fernando Alonso" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/alonso.jpg.img.1920.medium.jpg/1677244577162.jpg"
            "Max Verstappen" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/Verstappen.jpg.img.1920.medium.jpg/1677244577162.jpg"
            "Lewis Hamilton" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/Hamilton.jpg.img.1920.medium.jpg/1677244577162.jpg"
            "Sergio Pérez" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/Perez.jpg.img.1920.medium.jpg/1677244577162.jpg"
            "Carlos Sainz" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/sainz.jpg.img.1920.medium.jpg/1677069189406.jpg"
            "Charles Leclerc" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/leclerc.jpg.img.1920.medium.jpg/1677069223130.jpg"
            "George Russell" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/russell.jpg.img.1920.medium.jpg/1677069334466.jpg"
            "Lance Stroll" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/stroll.jpg.img.1920.medium.jpg/1677069453013.jpg"
            "Lando Norris" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/norris.jpg.img.1920.medium.jpg/1677069505471.jpg"
            "Pierre Gasly" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/gasly.jpg.img.1920.medium.jpg/1676983081984.jpg"
            "Nico Hülkenberg" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/hulkenberg.jpg.img.1920.medium.jpg/1676983071882.jpg"
            "Esteban Ocon" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/ocon.jpg.img.1920.medium.jpg/1677069269007.jpg"
            "Valtteri Bottas" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/bottas.jpg.img.1920.medium.jpg/1677069810695.jpg"
            "Oscar Piastri" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/piastri.jpg.img.1920.medium.jpg/1676983075734.jpg"
            "Guanyu Zhou" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/zhou.jpg.img.1920.medium.jpg/1677069909295.jpg"
            "Yuki Tsunoda" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/tsunoda.jpg.img.1920.medium.jpg/1677069846213.jpg"
            "Kevin Magnussen" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/magnussen.jpg.img.1920.medium.jpg/1677069387823.jpg"
            "Alexander Albon" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/albon.jpg.img.1920.medium.jpg/1677068770293.jpg"
            "Logan Sargeant" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/sargeant.jpg.img.1920.medium.jpg/1676983079144.jpg"
            "Nyck de Vries" -> "https://media.formula1.com/content/dam/fom-website/drivers/2023Drivers/devries.jpg.img.1920.medium.jpg/1676983081637.jpg"
            else -> "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Unknown_person.jpg/694px-Unknown_person.jpg"
        }

    }

}