package com.example.f1tracker.InterfacesApp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f1tracker.R

class MenuPrinicipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuInicioEleccion()
        }
    }
}

@Composable
@Preview
fun MenuInicioEleccion(){

    Column(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(top = 30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "F1 TRACKER",
                fontSize = 30.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.formula1bold))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .padding(start = 10.dp, end = 10.dp, top = 20.dp),
        ) {
            Opcion(
                "PILOTOS",
                "Aqui encontrarás información básica sobre los pilotos de la F1",
                R.mipmap.pilotoslogo
            )
            Opcion(
                "EQUIPOS",
                "Aqui encontrarás información básica sobre los equipos de la F1",
                R.mipmap.cochelogo
            )
            Opcion(
                "CIRCUITOS",
                "Aqui encontrarás información básica sobre los circuitos de la F1",
                R.mipmap.circuitoslogo
            )
            Opcion(
                "CALENDARIO",
                "Aqui encontrarás todos los calendarios de la Formula 1 desde 1950 hasta hoy",
                R.mipmap.calendariologo
            )
        }
    }
}

@Composable
fun Opcion(textoApartado: String, descripcionApartado: String, imagenApartado: Int){

    val contextoActual = LocalContext.current

    var actividad: Class<*>? = null // Variable para almacenar la actividad a la que se quiere ir

    // Segun el texto que se le pase, se le asigna una actividad
    when (textoApartado){
        "PILOTOS" -> {
            actividad = PilotosActivity::class.java
        }
        "EQUIPOS" -> {
            actividad = EscuderiasActivity::class.java
        }
        "CIRCUITOS" -> {
            actividad = CircuitosActivity::class.java
        }
        "CALENDARIO" -> {
            actividad = CalendarioActivity::class.java
        }
    }

    //Fila de la opcion
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(20.dp)
            .border(1.dp, Color.White)
            .clickable(
                //Si clickamos esa opcion nos llevara a la actividad correspondiente
                onClick = {
                    contextoActual.startActivity(Intent(contextoActual, actividad))
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        //Columna con la fila de la opcion y otra fila con la descripcion
        Column(
            modifier = Modifier
                .width(250.dp)
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                Text(
                    text = textoApartado,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.formula1bold))
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = descripcionApartado,
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Left,
                    fontFamily = FontFamily(Font(R.font.formula1regular))
                )
            }
        }
        //Columna con la imagen de la opcion
        Column(
            modifier = Modifier
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = imagenApartado),
                contentDescription = "Piloto",
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            )
        }
    }
}