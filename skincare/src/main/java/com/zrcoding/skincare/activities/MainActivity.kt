package com.zrcoding.skincare.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.zrcoding.skincare.common.preferences.Preferences
import com.zrcoding.skincare.features.navigation.MainNavHost
import com.zrcoding.skincare.theme.SkincareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val onboarded = intent.extras?.getBoolean(Preferences.ONBOARDING_COMPLETED_KEY) ?: return
        setContent {
            SkincareTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold { innerPadding ->
                        MainNavHost(
                            onboarded = onboarded,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}