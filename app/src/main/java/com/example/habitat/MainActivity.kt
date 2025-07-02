package com.example.habitat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.habitat.presentation.MainScreen
import com.example.habitat.ui.theme.HabitatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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