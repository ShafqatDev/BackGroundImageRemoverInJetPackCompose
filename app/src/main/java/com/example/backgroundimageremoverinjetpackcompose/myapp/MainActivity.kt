package com.example.backgroundimageremoverinjetpackcompose.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.backgroundimageremoverinjetpackcompose.presentation.bgm_remover.BackGroundImageRemoverScreen
import com.example.backgroundimageremoverinjetpackcompose.ui.theme.BackGroundImageRemoverInJetPackComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackGroundImageRemoverInJetPackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BackGroundImageRemoverScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
