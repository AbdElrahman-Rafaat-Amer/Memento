package com.abdelrahman.raafat.memento.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.abdelrahman.raafat.memento.MainActivity
import com.abdelrahman.raafat.memento.core.theme.MementoTheme
import com.abdelrahman.raafat.memento.ui.onboarding.ui.OnboardingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MementoTheme {
                Scaffold { paddingValues ->
                    OnboardingScreen(
                        modifier = Modifier
                            .padding(paddingValues),
                        onFinished = {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }

}