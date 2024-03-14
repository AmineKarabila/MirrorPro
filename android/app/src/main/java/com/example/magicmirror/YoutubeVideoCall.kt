package com.example.magicmirror
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import kotlinx.coroutines.*

import com.example.magicmirror.serverTalk.sendVideoIdToServer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeScreen(navController: NavController) {
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
        Button(onClick = { navController.navigate("home") }) {
            Text("Back to Home")
        }
        Text(text = response)
    }
}






