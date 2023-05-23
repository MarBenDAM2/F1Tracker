package com.example.f1tracker.InterfacesApp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.f1tracker.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AutenticacionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Autenticacion()
        }
    }
}

@Composable
fun Autenticacion() {
    val contextoActual = LocalContext.current
    var mostrarError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var email by rememberSaveable { mutableStateOf("") }
        Text(
            text = "Registrate o inicia sesion",
            color = Color.White,
            modifier = Modifier.padding(top = 40.dp),
            fontFamily = FontFamily(Font(R.font.formula1regular)),
            fontSize = 20.sp
        )
        Column (
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        text = "Email",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier
                    .padding(16.dp),
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

            var contra by rememberSaveable { mutableStateOf("") }

            TextField(
                value = contra,
                onValueChange = { contra = it },
                label = {
                    Text(
                        text = "Contraseña",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
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

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentWidth()
                    .padding(16.dp),
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .padding(
                            top = 50.dp
                        )
                        .wrapContentWidth()
                        .height(50.dp)
                    ,
                    onClick = {
                        if (email.isNotEmpty() && contra.isNotEmpty()){
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contra).addOnCompleteListener{
                                if (it.isSuccessful){
                                    //Intent a InicioAplicacion
                                    contextoActual.startActivity(Intent(contextoActual, InicioAplicacion::class.java))
                                } else {
                                    //Mostrar mensaje de error
                                    mostrarError = true
                                }
                            }
                        } else {
                            //Mostrar mensaje de error
                            mostrarError = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(0),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                }

                OutlinedButton(
                    modifier = Modifier
                        .padding(
                            top = 50.dp,
                            start = 16.dp
                        )
                        .wrapContentWidth()
                        .height(50.dp)
                    ,
                    onClick = {
                        if (email.isNotEmpty() && contra.isNotEmpty()){
                            CoroutineScope(Dispatchers.IO).launch{
                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, contra).addOnCompleteListener{
                                    if (it.isSuccessful){
                                        contextoActual.startActivity(Intent(contextoActual, InicioAplicacion::class.java))
                                    } else {
                                        //Mostrar mensaje de error
                                        mostrarError = true
                                    }
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(0),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text(
                        text = "Registro",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                }
            }
        }
    }

    if(mostrarError){
        AlertDialog(
            backgroundColor = Color.DarkGray,
            onDismissRequest = {
                mostrarError = false
            },
            title = {
                Text(
                    text = "Error",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.formula1regular))
                )
            },
            text = {
                Text(
                    text = "Ha ocurrido un error con el registro o el inicio de sesión, " +
                            "por favor compruebe la información ingresada",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.formula1regular))
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarError = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(0),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text(
                        text = "Aceptar",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.formula1regular))
                    )
                }
            }
        )
    }


}