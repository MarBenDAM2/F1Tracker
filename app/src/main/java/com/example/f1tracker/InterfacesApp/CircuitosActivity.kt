package com.example.f1tracker.InterfacesApp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.f1tracker.llamadasAPIRetrofit.CircuitosGETRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CircuitosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val CircuitoViewModel by viewModels<CircuitosGETRetrofit>()

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.DarkGray),
            ) {
                CircuitoInformacion(CircuitoViewModel)
            }
        }
    }

    @Composable
    fun CircuitoInformacion(CircuitoViewModel: CircuitosGETRetrofit){
        val contextoActual = LocalContext.current
        val TAM_IMAGEN = 250.dp
        var mostrarInfo by remember { mutableStateOf(false) }
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
                        text = "Busca un circuito",
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
                            CircuitoViewModel.busquedaPorNombre(texto_busqueda.value)
                        }
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
                ),
                textStyle = TextStyle(
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.formula1regular))
                )
            )
        }

        if (mostrarInfo){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
                    .background(color = Color.DarkGray),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                AsyncImage(
                    model = CircuitoViewModel.linkFoto,
                    contentDescription = "Imagen del circuito",
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .width(TAM_IMAGEN)
                        .height(TAM_IMAGEN),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(color = Color.DarkGray),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Nombre:",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.formula1regular))
                        )
                        Text(
                            text = CircuitoViewModel.nom_circuito,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 10.dp),
                            fontFamily = FontFamily(Font(R.font.formula1regular))
                        )
                        Text(
                            text = "Más información:",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.formula1regular))
                        )
                        Text(
                            text = CircuitoViewModel.linkCircuito,
                            fontFamily = FontFamily(Font(R.font.formula1regular)),
                            color = Color.White,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .clickable {
                                    val uri = Uri.parse(CircuitoViewModel.linkCircuito)
                                    contextoActual.startActivity(Intent(Intent.ACTION_VIEW, uri))
                                }
                                .padding(bottom = 10.dp),
                            textAlign = TextAlign.Center
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Localización:",
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.formula1regular)),
                                    modifier = Modifier
                                        .padding(bottom = 10.dp)
                                )
                                Row{
                                    Column{
                                        Text(
                                            text = "Latitud: ${CircuitoViewModel.latitudCircuito}",
                                            color = Color.White,
                                            fontFamily = FontFamily(Font(R.font.formula1regular))
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column{
                                        Text(
                                            text = "Longitud: ${CircuitoViewModel.longitudCircuito}",
                                            color = Color.White,
                                            fontFamily = FontFamily(Font(R.font.formula1regular))
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                Row{
                                    Column {
                                        Text(
                                            text = "Pais: ${CircuitoViewModel.paisCircuito}",
                                            color = Color.White,
                                            fontFamily = FontFamily(Font(R.font.formula1regular))
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "Localidad: ${CircuitoViewModel.localidadCircuito}",
                                            color = Color.White,
                                            fontFamily = FontFamily(Font(R.font.formula1regular))
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
