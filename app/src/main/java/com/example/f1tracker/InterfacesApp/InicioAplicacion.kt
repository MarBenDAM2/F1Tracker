package com.example.f1tracker.InterfacesApp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.f1tracker.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.delay

class InicioAplicacion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Carousel()
            FondoGradiente()
        }
    }
}

@Composable
fun FondoGradiente(){
    val contextoActual = LocalContext.current
    val colores = arrayOf(
        0.0f to Color(Color.Unspecified.value),
        0.8f to Color(Color(94,94,94).value),
        1.0f to Color(Color.DarkGray.value)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .background(
                brush = Brush.verticalGradient(
                    colorStops = colores,
                    endY = 1200f
                )
            )

    ){
        Column(
            modifier = Modifier
                .padding(
                    top = 400.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .background(
                        color = Color.Transparent
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp,
                            topStart = 20.dp,
                            topEnd = 20.dp
                        )
                    )
                    .padding(all = 20.dp)
            ) {
                Text(
                    text = "Bienvenido a F1 Tracker",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .background(
                        color = Color.Transparent
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp,
                            topStart = 20.dp,
                            topEnd = 20.dp
                        )
                    )
                    .padding(all = 20.dp)
            ) {
                Text(
                    text = "En esta aplicacion podrás encontrar información sobre pilotos, " +
                            "escuderias y carreras de la Fórmula 1",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(
                        top = 50.dp
                    )
                    .fillMaxWidth()
                    .height(50.dp)
                ,
                onClick = {
                    contextoActual.startActivity(Intent(contextoActual, MenuPrinicipal::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(0),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(
                    text = "Iniciar",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Carousel(){
    val images = listOf(
        R.mipmap.fotocarrusel1,
        R.mipmap.fotocarrusel2,
        R.mipmap.fotocarrusel3,
        R.mipmap.fotocarrusel4,
        R.mipmap.fotocarrusel5
    )
    Card(
        shape = RoundedCornerShape(0.dp),
    ) {
        AutoSlidingCarousel(
            itemsCount = images.size,
            itemContent = { index ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(images[index])
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(340.dp)
                )
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 6000,
    pagerState: PagerState = remember { PagerState() },
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {

    LaunchedEffect(pagerState.currentPage) {
        delay(autoSlideDuration)
        pagerState.animateScrollToPage((pagerState.currentPage + 1) % itemsCount)
    }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }
    }
}