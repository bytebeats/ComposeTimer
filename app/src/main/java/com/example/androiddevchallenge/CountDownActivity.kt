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
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme

class CountDownActivity : AppCompatActivity() {

    var started: Boolean = false

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
    return if (started) "Stop" else "Start"
}

@Composable
fun CountDownLayout(seconds: Int, darkTheme: Boolean) {
    val started = remember { mutableStateOf(false) }
    val progress = remember { mutableStateOf(0) }
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(
                    text = AnnotatedString(text = getActionText(started.value)),
                    onClick = { started.value = !started.value }
                )
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(
                    modifier = Modifier
                        .size(300.dp, 300.dp)
                ) {
                    val circleColor = if (darkTheme) Color.White else Color.DarkGray
                    val progressColor = if (darkTheme) Color.Blue else Color.Blue
                    drawArc(
                        color = circleColor,
                        startAngle = -90F,
                        sweepAngle = 360F,
                        useCenter = false,
                        style = Stroke(width = 10F),
                    )

                    drawArc(
                        color = progressColor,
                        startAngle = -90F,
                        sweepAngle = -90F,
                        useCenter = false,
                        style = Stroke(width = 10F),
                    )

                    drawCircle(color = progressColor, radius = 5F,)
                }
            }
        }
    }
}
