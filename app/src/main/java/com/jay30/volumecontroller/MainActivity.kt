package com.jay30.volumecontroller

import android.content.Context
import android.content.Context.AUDIO_SERVICE
import android.media.AudioManager
import android.media.AudioManager.ADJUST_MUTE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jay30.volumecontroller.ui.theme.VolumeControllerTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VolumeControllerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "VolumeController",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                })
                        }) { innerPadding ->
                        Column(
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            volumeController(LocalContext.current)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun volumeController(context: Context) {
    val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager
    val currentVolume = remember { mutableStateOf(0) }

    fun updateVolume() {
        val volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolumeLevel = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val volumePercent = (volumeLevel.toFloat() / maxVolumeLevel * 100).toInt()
        currentVolume.value = (volumeLevel.toFloat() / maxVolumeLevel * 100).toInt()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Volume Controller",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Text(
            text = "Volume Level : " + currentVolume.value,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )

        Row(modifier = Modifier.padding(4.dp)) {
            Button(
                onClick = {
                    audioManager.adjustVolume(
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_PLAY_SOUND
                    )
                    updateVolume()
                },
                modifier = Modifier.padding(all = Dp(10F)),
                enabled = true,
                border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(text = "+")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Button(
                onClick = {
                    audioManager.adjustVolume(
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_PLAY_SOUND
                    )
                    updateVolume()
                },


                modifier = Modifier.padding(all = Dp(10F)),
                enabled = true,

                border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(text = "-")
            }
        }
        Row {
            Button(
                onClick = {
                    audioManager.adjustVolume(
                        AudioManager.ADJUST_MUTE,
                        AudioManager.FLAG_PLAY_SOUND,
                    )
                },
                modifier = Modifier.padding(all = Dp(10F)),
                enabled = true,

                border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text("Mute")
            }

            Button(
                onClick = {
                    audioManager.adjustVolume(
                        AudioManager.ADJUST_UNMUTE,
                        AudioManager.FLAG_PLAY_SOUND,
                    )
                },
                modifier = Modifier.padding(all = Dp(10F)),
                enabled = true,

                border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text("Unmute")
            }
        }
        Row {
            Button(
                onClick = {
                    audioManager.adjustVolume(
                        AudioManager.ADJUST_TOGGLE_MUTE,
                        AudioManager.FLAG_PLAY_SOUND,
                    )
                },
                modifier = Modifier.padding(all = Dp(10F)),
                enabled = true,

                border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text("Toggle Mute state")
            }
        }
    }
}
