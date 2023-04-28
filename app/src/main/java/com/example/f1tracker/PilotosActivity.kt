package com.example.f1tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PilotosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        setContent {

        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ergast.com/api/f1/drivers.json?limit=847")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
