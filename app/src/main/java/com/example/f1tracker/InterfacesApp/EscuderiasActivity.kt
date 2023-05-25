package com.example.f1tracker.InterfacesApp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.f1tracker.R
import com.example.f1tracker.llamadasAPIRetrofit.EscuderiaGETRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        val contextoActual = LocalContext.current
        val TAM_IMAGEN = 250.dp


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
                label = {
                    Text(
                        text = "Busca una escuderia",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        CoroutineScope(Dispatchers.IO).launch {
                            EscuderiaViewModel.busquedaPorNombre(texto_busqueda.value)
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
        /////////////////////////// BUSQUEDA POR NOMBRE ///////////////////////////

        //Si la variable encontrado es verdadera se mostrará la información.
        if (EscuderiaViewModel.encontrado){
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
                    model = EscuderiaViewModel.linkFoto,
                    contentDescription = "Imagen del equipo",
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .width(TAM_IMAGEN)
                        .height(TAM_IMAGEN),
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
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                    Text(
                        text = "Nacionalidad: ${EscuderiaViewModel.nacionalidad}",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Más información:",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.formula1regular))
                        )
                        //Texto clickeable que nos lleva a la wikipedia del constructor
                        Text(
                            text = EscuderiaViewModel.urlEscuderia,
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        val uri = Uri.parse(EscuderiaViewModel.urlEscuderia)
                                        contextoActual.startActivity(Intent(Intent.ACTION_VIEW, uri))
                                    }
                                ),
                            color = Color.White,
                            textDecoration = TextDecoration.Underline,
                            fontFamily = FontFamily(Font(R.font.formula1regular)),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                /////////////////////////// INFORMACIÓN DEL CONSTRUCTOR ///////////////////////////
            }
            /////////////////////////// INFORMACIÓN DEL CONSTRUCTOR ///////////////////////////
        }

    }
}

