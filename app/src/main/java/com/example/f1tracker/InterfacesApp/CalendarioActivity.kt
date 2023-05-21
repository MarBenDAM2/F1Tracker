package com.example.f1tracker.InterfacesApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f1tracker.llamadasAPIRetrofit.CalendarioGETRetrofit
import java.util.Calendar

class CalendarioActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val CalendarioViewModel by viewModels<CalendarioGETRetrofit>()

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.DarkGray),
            ) {
                CalendarioInformacion(CalendarioViewModel)
            }
        }
    }

    @Composable
    fun CalendarioInformacion(CalendarioViewModel: CalendarioGETRetrofit) {
        val ANCHO_CELDA = 230.dp
        val ANCHO_NUMERO = 50.dp
        val ALTO_FECHANUMERO = 35.dp

        /////////////// SELECCION DE AÑO //////////
        var mostrarInfo by remember { mutableStateOf(false) }
        var yearActual = Calendar.getInstance().get(Calendar.YEAR)

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
                label = { androidx.compose.material.Text(text = "Escribe un año entre 1950 y $yearActual", color = Color.White) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        CalendarioViewModel.busquedaPorYear(texto_busqueda.value)
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

        /*
          Esto antes era un dropdown, pero no existe en Jetpack Compose un "onChange" (...), por tanto
          se queda como un campo de texto numeral con control de errores entre 1950 y el año actual
          (2023)
        */
        /////////////// SELECCION DE AÑO //////////

        //DATOS//
        if (mostrarInfo) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
            ){
                items(CalendarioViewModel.listaCarreras.size){
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Column(
                            modifier = Modifier
                                .width(ANCHO_NUMERO)
                                .height(ALTO_FECHANUMERO)
                                .padding(
                                    horizontal = 10.dp
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.White
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = CalendarioViewModel.listaCarreras[it].numCarrera,
                                color = Color.White,
                                fontSize = 13.sp,
                                textAlign = TextAlign.End
                            )
                        }

                        Column(
                            modifier = Modifier
                                .width(ANCHO_CELDA)
                                .wrapContentHeight()
                                .border(
                                    width = 1.dp,
                                    color = Color.White
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ){

                            Row(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                            ){
                                Text(
                                    text = CalendarioViewModel.listaCarreras[it].nombreCarrera,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    textAlign = TextAlign.End
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                            ) {
                                Text(
                                    text = CalendarioViewModel.listaCarreras[it].nombreCircuito,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    textAlign = TextAlign.End
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .height(ALTO_FECHANUMERO)
                                .padding(horizontal = 10.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.White
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = CalendarioViewModel.listaCarreras[it].fechaCarrera,
                                color = Color.White,
                                fontSize = 12.5.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                            )
                        }

                    }
                }
            }
        }
        //DATOS//
    }
}
