package com.example.f1tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

class CalendarioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showText by remember { mutableStateOf(false) }

            Column {
                Button(onClick = { showText = !showText }) {
                    Text(if (showText) "Ocultar texto" else "Mostrar texto")
                }

                if (showText) {
                    Text("Texto oculto")
                }
            }
        }
    }
}

@Composable
fun CajaEleccionPiloto(){

    val lista_pilotos: Int = 0 //Pendiente de implementación de API

    var estaExpandido by remember {
        mutableStateOf(false)
    }

    var pilotoSeleccionado by remember {
        mutableStateOf(lista_pilotos) //Pendiente de implementación de API
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()

    ) {
        DropdownMenu(expanded = estaExpandido, onDismissRequest = { estaExpandido = false }) {

        }
    }

}