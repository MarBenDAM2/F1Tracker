package com.example.f1tracker.InterfacesApp

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.f1tracker.R
import com.example.f1tracker.llamadasAPIRetrofit.PilotosGETRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PilotosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val PilotosGETRetrofit by viewModels<PilotosGETRetrofit>()
        setContent {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .background(color = Color.DarkGray),
            ) {
                PilotoInformacion(PilotosGETRetrofit)
            }
        }
    }
    @Composable
    fun PilotoInformacion(PilotosGETRetrofit: PilotosGETRetrofit) {

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
                        text = "Busca un piloto",
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
                        CoroutineScope(Dispatchers.IO).launch{
                            PilotosGETRetrofit.busquedaPorNombre(texto_busqueda.value)
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
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.formula1regular))
                )
            )
        }
        /////////////////////////// BUSQUEDA POR NOMBRE ///////////////////////////

        if (PilotosGETRetrofit.encontrado){
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
                    model = PilotosGETRetrofit.linkFoto,
                    contentDescription = "Imagen del piloto",
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .width(TAM_IMAGEN)
                        .height(TAM_IMAGEN),
                )
                /////////////////////////// IMAGEN DEL PILOTO ///////////////////////////
                Spacer(modifier = Modifier.height(30.dp))

                /////////////////////////// NOMBRE Y NÚMERO DEL PILOTO ///////////////////////////
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nombre: ${PilotosGETRetrofit.nom_piloto}",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Número: ${PilotosGETRetrofit.num_perma}",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                }
                /////////////////////////// NOMBRE Y NÚMERO DEL PILOTO ///////////////////////////

                /////////////////////////// NACIMIENTO Y NOMBRE CORTO DEL PILOTO ///////////////////////////
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Fecha de nacimiento: ${PilotosGETRetrofit.fecha_nacimiento}",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nombre corto: ${PilotosGETRetrofit.nomCortoPiloto}",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nacionalidad: ${PilotosGETRetrofit.nacionalidad}",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                }
            }
        }
    }
}
