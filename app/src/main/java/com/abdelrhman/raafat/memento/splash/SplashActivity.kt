package com.abdelrhman.raafat.memento.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
                AnimatedSplashScreen(
                    onFinished = {
                        startActivity(Intent(this, OnboardingActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}
