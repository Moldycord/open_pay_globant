package com.danieer.galvez.openpaytest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.danieer.galvez.openpaytest.navigation.AppNavHost
import com.danieer.galvez.openpaytest.ui.theme.OpenPayTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenPayTestTheme {

                AppNavHost()
            }
        }
    }
}