package com.example.habitat

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.example.habitat.presentation.MainScreen
import com.example.habitat.ui.theme.HabitatTheme
import com.example.habitat.ui.theme.backgroundColorLight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                scrim = backgroundColorLight.toArgb(),
                darkScrim = backgroundColorLight.toArgb()
            )
        )

        setContent {
            HabitatTheme {
                MainScreen()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AppPreview() {
    HabitatTheme {
        MainScreen()
    }
}