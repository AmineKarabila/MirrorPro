package com.example.magicmirror

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.*
import com.example.magicmirror.serverTalk.sendVideoIdToServer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeScreen(navController: NavController) {
    // MaterialTheme umgibt Ihren Composable für das Farbschema
    MaterialTheme(
        colorScheme = darkColorScheme()
    ){
        // Surface für Hintergrundfarbe basierend auf dem Farbschema
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var videoId by remember { mutableStateOf("") }
            var response by remember { mutableStateOf("") }

            // Quadrat mit 5x5 Grid
            val gridSize = 5
            val gridItems = remember { mutableStateListOf<Boolean>().apply { addAll(List(gridSize * gridSize) { false }) } }
            Column(
                modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {



                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
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
        }
    }
}
