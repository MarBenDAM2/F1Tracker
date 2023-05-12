package com.example.f1tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

class EscuderiasActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val EscuderiaViewModel by viewModels<EscuderiaGETRetrofit>()
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.DarkGray),
            ) {
                EscuderiaInformacion(EscuderiaViewModel)
            }

        }

    }
    @Composable
    fun EscuderiaInformacion(EscuderiaViewModel: EscuderiaGETRetrofit){
        var mostrarInfo by remember { mutableStateOf(false) }

        /////////////////////////// BUSQUEDA POR NOMBRE ///////////////////////////
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
        ) {
            val texto_busqueda = rememberSaveable { mutableStateOf("") }
            mostrarInfo = true
            TextField(
                value = texto_busqueda.value,
                onValueChange = {
                    texto_busqueda.value = it
                },
                label = { Text(text = "Busca una escuderia", color = Color.White) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        EscuderiaViewModel.busquedaPorNombre(texto_busqueda.value)

                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                )
            )
        }
        /////////////////////////// BUSQUEDA POR NOMBRE ///////////////////////////

        if (mostrarInfo){
            /////////////////////////// INFORMACIÓN DEL CONSTRUCTOR ///////////////////////////
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Color.DarkGray),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                /////////////////////////// IMAGEN DEL CONSTRUCTOR ///////////////////////////
                AsyncImage(
                    model = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Unknown_person.jpg/694px-Unknown_person.jpg",
                    contentDescription = "Imagen del equipo",
                    modifier = Modifier
                        .padding(top = 50.dp),
                )
                /////////////////////////// IMAGEN DEL CONSTRUCTOR ///////////////////////////
                Spacer(modifier = Modifier.height(30.dp))
                /////////////////////////// NOMBRE Y NACIONALIDAD DEL CONSTRUCTOR ///////////////////////////
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Nombre: ${EscuderiaViewModel.nom_escuderia}",
                        color = Color.White
                    )
                    Text(
                        text = "Nacionalidad: ${EscuderiaViewModel.nacionalidad}",
                        color = Color.White
                    )
                }
                /////////////////////////// NOMBRE Y NACIONALIDAD DEL CONSTRUCTOR ///////////////////////////

                /////////////////////////// INFORMACIÓN DEL CONSTRUCTOR ///////////////////////////
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Más información: ${EscuderiaViewModel.urlEscuderia}",
                        color = Color.White
                    )
                }
                /////////////////////////// INFORMACIÓN DEL CONSTRUCTOR ///////////////////////////

            }
            /////////////////////////// INFORMACIÓN DEL CONSTRUCTOR ///////////////////////////
        }


    }
}

