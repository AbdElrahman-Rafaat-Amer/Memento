package com.abdelrahman.raafat.memento.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.abdelrahman.raafat.memento.MainActivity
import com.abdelrahman.raafat.memento.ui.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.onboarding.OnboardingActivity
import com.abdelrahman.raafat.memento.ui.splash.model.Destination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MementoTheme {
                val destination by viewModel.startDestination.collectAsState()

                Scaffold { paddingValues ->
                    AnimatedSplashScreen(
                        modifier = Modifier.padding(paddingValues),
                        onFinished = {
                            when (destination) {
                                Destination.Onboarding -> openOnboarding()
                                Destination.Main -> openMain()
                            }
                        }
                    )
                }
            }
        }
    }

    private fun openOnboarding() {
        startActivity(Intent(this, OnboardingActivity::class.java))
        finish()
    }

    private fun openMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
