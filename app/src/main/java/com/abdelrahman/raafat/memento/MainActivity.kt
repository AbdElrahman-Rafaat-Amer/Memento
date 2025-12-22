package com.abdelrahman.raafat.memento

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MementoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MementoApp(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun MementoApp(modifier: Modifier) {
    val navController = rememberNavController()
    Box(
        modifier = modifier
    ) {
        MemoNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController
        )
    }
}