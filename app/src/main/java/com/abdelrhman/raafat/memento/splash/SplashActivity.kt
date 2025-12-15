package com.abdelrhman.raafat.memento.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.abdelrhman.raafat.memento.core.theme.MementoTheme
import com.abdelrhman.raafat.memento.onboarding.OnboardingActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MementoTheme {
                Scaffold { paddingValues ->
                    AnimatedSplashScreen(
                        modifier = Modifier.padding(paddingValues),
                        onFinished = {
                            startActivity(Intent(this, OnboardingActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}
