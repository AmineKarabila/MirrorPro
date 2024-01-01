package com.example.magicmirror

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*


import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface VideoService {
    @POST("/play-video")
    suspend fun playVideo(@Body videoId: VideoId): Response<String>
}

data class VideoId(val videoId: String)



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var videoId by remember { mutableStateOf("") }
            var response by remember { mutableStateOf("") }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = videoId,
                    onValueChange = { videoId = it },
                    label = { Text("Video ID") }
                )
                Button(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val result = sendVideoIdToServer(videoId)
                        withContext(Dispatchers.Main) {
                            response = result
                        }
                    }
                }) {
                    Text("Video starten")
                }
                Text(text = response)
            }
        }
    }



    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.2.103:3001") // Ersetze [DEINE_SERVER_ADRESSE] mit der tatsächlichen Adresse deines Servers
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val videoService = retrofit.create(VideoService::class.java)

    private suspend fun sendVideoIdToServer(videoId: String): String {
        return try {
            val response = videoService.playVideo(VideoId(videoId))
            if (response.isSuccessful) {
                response.body() ?: "Erfolg, aber keine Antwort vom Server"
            } else {
                "Fehler: ${response.code()}"
            }
        } catch (e: Exception) {
            "Netzwerkfehler: ${e.message}"
        }
    }



}
