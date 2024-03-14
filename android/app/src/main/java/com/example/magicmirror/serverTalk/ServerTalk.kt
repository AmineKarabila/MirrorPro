package com.example.magicmirror.serverTalk
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


private val retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.2.130:3000") // Verwende die IP-Adresse deines Raspberry Pi
    .addConverterFactory(GsonConverterFactory.create())
    .build()


private val videoService = retrofit.create(VideoService::class.java)

suspend fun sendVideoIdToServer(videoId: String): String {
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