package com.example.mediastore

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mediastore.ui.theme.MediaStoreTheme
import kotlinx.coroutines.runBlocking


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)

        val mediaStoreUtil = MediaStoreUtil(this)
        val file = mediaStoreUtil.getRawAudioFile(R.raw.datschenma)
        runBlocking {
            mediaStoreUtil.saveAudio(file)
        }
        setContent {
            MediaStoreTheme {

            }
        }
    }

}