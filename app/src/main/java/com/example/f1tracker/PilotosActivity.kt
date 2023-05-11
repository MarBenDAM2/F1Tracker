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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

class PilotosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val PilotosViewModel by viewModels<PilotosViewModel>()
        setContent {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.DarkGray),
            ) {
                PilotoInformacion(PilotosViewModel)
            }

        }
    }
    @Composable
    fun PilotoInformacion(PilotosViewModel: PilotosViewModel) {
        var mostrarInfo by remember { mutableStateOf(false) }

        /////////////////////////// BUSQUEDA POR NOMBRE ///////////////////////////
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
        ) {
            val texto_busqueda = rememberSaveable { mutableStateOf("") }
            TextField(
                value = texto_busqueda.value,
                onValueChange = {
                    texto_busqueda.value = it
                },
                label = { Text(text = "Busca un piloto", color = Color.White) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        PilotosViewModel.busquedaPorNombre(texto_busqueda.value)
                        mostrarInfo = true
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
            /////////////////////////// INFORMACIÓN DEL PILOTO ///////////////////////////
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Color.DarkGray),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ){
                /////////////////////////// IMAGEN DEL PILOTO ///////////////////////////
                AsyncImage(
                    model = PilotosViewModel.pilotoLink(),
                    contentDescription = "Imagen del piloto",
                    modifier = Modifier
                        .padding(top = 50.dp),
                )
                /////////////////////////// IMAGEN DEL PILOTO ///////////////////////////
                Spacer(modifier = Modifier.height(30.dp))

                /////////////////////////// NOMBRE Y NÚMERO DEL PILOTO ///////////////////////////
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Nombre: ${PilotosViewModel.nom_piloto}",
                        color = Color.White
                    )
                    Text(
                        text = "Número: ${PilotosViewModel.num_perma}",
                        color = Color.White
                    )
                }
                /////////////////////////// NOMBRE Y NÚMERO DEL PILOTO ///////////////////////////

                /////////////////////////// NACIMIENTO Y NOMBRE CORTO DEL PILOTO ///////////////////////////
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Fecha de nacimiento: ${PilotosViewModel.fecha_nacimiento}",
                        color = Color.White
                    )
                    Text(
                        text = "Nombre corto: ${PilotosViewModel.nomCortoPiloto}",
                        color = Color.White
                    )
                }
                /////////////////////////// NACIMIENTO Y NOMBRE CORTO DEL PILOTO ///////////////////////////

                /////////////////////////// NACIONALIDAD DEL PILOTO ///////////////////////////
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nacionalidad: ${PilotosViewModel.nacionalidad}",
                        color = Color.White
                    )
                }
                /////////////////////////// NACIONALIDAD DEL PILOTO ///////////////////////////

            }
            /////////////////////////// INFORMACIÓN DEL PILOTO ///////////////////////////
        }


    }



}
