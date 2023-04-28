package com.example.f1tracker

import android.content.Intent
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MenuPrinicipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColumnaConBotones()
        }
    }
}
@Composable
fun ColumnaConBotones(){
    val TAM = 120
    Column(
        modifier = Modifier
            .background(
                color = Color.DarkGray
            )
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            Modifier.padding(top = 60.dp)
        ) {
            Text(
                text = "F1 TRACKER",
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 40.sp
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 60.dp)
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            BotonCaja("Pilotos", R.mipmap.pilotoslogo, TAM, TAM, PilotosActivity::class.java)
            BotonCaja("Escuderias", R.mipmap.cochelogo, TAM, TAM, EscuderiasActivity::class.java)
        }
        Row(
            modifier = Modifier
                .padding(top = 60.dp)
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            BotonCaja("Circuitos", R.mipmap.circuitoslogo, TAM, TAM, CircuitosActivity::class.java)
            BotonCaja("Calendario", R.mipmap.calendariologo, TAM, TAM, CalendarioActivity::class.java)
        }

    }
}
@Composable
fun BotonCaja(texto: String, foto: Int, alto: Int, ancho: Int, Activity: Class<*>){
    val contextoActual = LocalContext.current
    var actividad: Class<*>? = null
    when (texto){
        "Pilotos" -> actividad = PilotosActivity::class.java
        "Escuderias" -> actividad = EscuderiasActivity::class.java
        "Circuitos" -> actividad = CircuitosActivity::class.java
        "Calendario" -> actividad = CalendarioActivity::class.java
    }
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .padding(20.dp)
            .clickable(
                onClick = {
                    contextoActual.startActivity(Intent(contextoActual, actividad))
                }
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(foto),
                contentDescription = null,
                modifier = Modifier
                    .size(ancho.dp, alto.dp)
            )
            Spacer(modifier = Modifier.size(50.dp))
            Text(
                text = texto,
                modifier = Modifier
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 25.sp,
            )
        }

    }

}