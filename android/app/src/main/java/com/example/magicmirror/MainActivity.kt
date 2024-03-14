package com.example.magicmirror

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}


    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "home") {
            composable("home") { HomeScreen(navController) }
            composable("youtube") { YouTubeScreen(navController) } // Bereits vorhanden
            composable("calendar") { CalendarScreen(navController) }
            // Füge hier weitere Bildschirme hinzu
        }
    }


