package com.zrcoding.skincare.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zrcoding.skincare.common.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                val onboarded =
                    preferences.readBoolean(Preferences.ONBOARDING_COMPLETED_KEY).first()
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.putExtra(Preferences.ONBOARDING_COMPLETED_KEY, onboarded)
                startActivity(intent)
                finish()
            }
        }
    }
}