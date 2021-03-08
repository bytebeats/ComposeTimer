/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.util.getFullTime
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class CountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                CountDownLayout(seconds = intent.getIntExtra("time", 0), isSystemInDarkTheme())
            }
        }
    }
}

fun getActionText(started: Boolean): String {
    return if (started) "Stop" else "Restart"
}

@Composable
fun CountDownLayout(seconds: Int, darkTheme: Boolean) {
    val started = remember { mutableStateOf(true) }
    val finished = remember { mutableStateOf(false) }
    val progress = remember { mutableStateOf(seconds * 100) }

    var countDownTimer = object : CountDownTimer(seconds * 1000L, 10L) {
        override fun onFinish() {
            finished.value = true
        }

        override fun onTick(millisUntilFinished: Long) {
            progress.value = (millisUntilFinished / 10L).toInt()
        }
    }
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        started.value = !started.value
                        if (started.value) {
                            finished.value = false
                            countDownTimer.start()
                        } else {
                            countDownTimer.cancel()
                        }
                    }
                ) {
                    Text(text = getActionText(started.value), fontSize = 20.sp)
                }
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                val progressStrokeColor = if (darkTheme) Color.Blue else Color.Blue
                val progressSize = 250.dp
                Box(
                    modifier = Modifier.size(progressSize),
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Canvas(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val radius = size.width / 2.0F
                            val circleStrokeColor = if (darkTheme) Color.White else Color.DarkGray
                            drawArc(
                                color = circleStrokeColor,
                                startAngle = -90F,
                                sweepAngle = 360F,
                                useCenter = false,
                                style = Stroke(width = 10F),
                            )
                            val ratio = progress.value.toFloat() / seconds / 100
                            val sweepAngle = 360F * (ratio - 1)
                            drawArc(
                                color = progressStrokeColor,
                                startAngle = -90F,
                                sweepAngle = sweepAngle,
                                useCenter = false,
                                style = Stroke(width = 10F),
                            )
                            val x = radius + radius * sin(-PI - 2 * PI * ratio)
                            val y = radius + radius * cos(-PI - 2 * PI * ratio)
                            drawCircle(
                                color = progressStrokeColor,
                                radius = 20F,
                                center = Offset(x.toFloat(), y.toFloat()),
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (finished.value) {
                            Text(
                                text = "Finished",
                                style = MaterialTheme.typography.h3,
                                fontSize = 40.sp,
                            )
                        } else {
                            val textSize = if (progress.value < 10000) 70.sp
                            else if (progress.value > 1000000) 45.sp
                            else 55.sp
                            Text(
                                text = getFullTime(progress.value / 100),
                                color = progressStrokeColor,
                                fontSize = textSize,
                            )
                        }
                    }
                }
            }
        }
    }
    countDownTimer.start()
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreviewCountDown() {
    MyTheme {
        CountDownLayout(20, false)
    }
}
