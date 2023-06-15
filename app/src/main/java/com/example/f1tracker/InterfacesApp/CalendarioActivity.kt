package com.example.f1tracker.InterfacesApp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f1tracker.R
import com.example.f1tracker.llamadasAPIRetrofit.CalendarioGETRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        val contextoActual = LocalContext.current

        val ANCHO_CELDA_NOMBRE = 230.dp
        val ANCHO_CELDA_FECHA = 100.dp
        val ANCHO_NUMERO = 50.dp
        val ALTO_FECHANUMERO = 35.dp
        val ALTO_NOMBRES = 22.dp
        val TAM_TEXTO = 10.sp

        /////////////// SELECCION DE AÑO //////////
        val yearActual = Calendar.getInstance().get(Calendar.YEAR)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
        ) {
            //rememberSaveable es para que no se borre el texto al rotar la pantalla
            val texto_busqueda = rememberSaveable { mutableStateOf("") }
            TextField(
                value = texto_busqueda.value,
                onValueChange = {
                    texto_busqueda.value = it
                },
                label = {
                    Text(
                        text = "Escribe un año entre 1950 y $yearActual",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        //Si el año está entre 1950 y 2023 ejecutamos la busqueda en modo de corrutina
                        try {
                            if (texto_busqueda.value.toInt() in 1950..yearActual) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    CalendarioViewModel.busquedaPorYear(texto_busqueda.value)
                                }
                            } else {
                                //Si no, mostramos un toast de error de año
                                Toast.makeText(
                                    contextoActual,
                                    "Introduce un año entre 1950 y $yearActual",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: NumberFormatException){
                            Toast.makeText(
                                contextoActual,
                                "Introduce un año sin simbolos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

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
                ),
                textStyle = TextStyle(
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.formula1regular))
                )
            )
        }

        /*
          Esto antes era un dropdown, pero no existe en Jetpack Compose un "onChange" (...), por tanto
          se queda como un campo de texto numeral con control de errores entre 1950 y el año actual
          (2023)
        */
        /////////////// SELECCION DE AÑO //////////

        //Mostramos la columna solamente si ha encontrado los datos
        // (de tal forma que cuando entremos no se mostrará nada)
        if (CalendarioViewModel.terminado) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(5.dp)
            ){
                items(CalendarioViewModel.listaCarreras.size){

                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .clickable(onClick = {
                                //Hacemos que cada fila que se genere sea clickable con el enlace de cada carrera
                                val uri = Uri.parse(CalendarioViewModel.listaCarreras[it].urlCarrera)
                                contextoActual.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            }),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        //Columna 1 con el numero de carrera
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
                                fontSize = TAM_TEXTO,
                                textAlign = TextAlign.End,
                                fontFamily = FontFamily(Font(R.font.formula1regular))
                            )
                        }
                        //Columna 2 con el nombre de la carrera
                        Column(
                            modifier = Modifier
                                .width(ANCHO_CELDA_NOMBRE)
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
                                    .height(ALTO_NOMBRES),
                            ){
                                Text(
                                    text = CalendarioViewModel.listaCarreras[it].nombreCarrera,
                                    color = Color.White,
                                    fontSize = TAM_TEXTO,
                                    textAlign = TextAlign.End,
                                    fontFamily = FontFamily(Font(R.font.formula1regular)),
                                    modifier = Modifier
                                        .padding(top = 1.dp)
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
                                    fontSize = TAM_TEXTO,
                                    textAlign = TextAlign.End,
                                    fontFamily = FontFamily(Font(R.font.formula1regular)),
                                    modifier = Modifier
                                        .padding(bottom = 1.dp)
                                )
                            }
                        }
                        //Columna 3 con la fecha de la carrera
                        Column(
                            modifier = Modifier
                                .width(ANCHO_CELDA_FECHA)
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
                                fontSize = TAM_TEXTO,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.formula1regular)),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
