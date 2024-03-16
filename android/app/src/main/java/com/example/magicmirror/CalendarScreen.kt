
package com.example.magicmirror

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment

@Composable
fun CalendarScreen(navController: NavController) {
    MaterialTheme(
        colorScheme = darkColorScheme() // Verwendet das dunkle Farbschema
    ) {
        // Surface passt seine Farbe basierend auf dem Farbschema an
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // Setzt die Hintergrundfarbe entsprechend des Farbschemas
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // Zentriert den Inhalt horizontal
                verticalArrangement = Arrangement.Center // Zentriert den Inhalt vertikal
            ) {
                Text(text = "Calendar Screen", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { navController.navigate("home") }) {
                    Text("Back to Home")
                }
            }
        }
    }
}