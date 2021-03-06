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

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.util.calculate
import com.example.androiddevchallenge.util.getHours
import com.example.androiddevchallenge.util.getMinutes
import com.example.androiddevchallenge.util.getSeconds

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val context = LocalContext.current
    val time = remember { mutableStateOf("") }
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 15.dp,
                    end = 10.dp,
                    bottom = 15.dp
                )
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .height(80.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                val timeTextColor =
                    if (time.value.isEmpty()) Color.White else Color.Cyan
                val timeTextSize = 60.sp
                val timeUnitTextSize = 20.sp
                val timeModifier = Modifier.padding(end = 15.dp)
                Text(
                    text = getHours(time.value),
                    textAlign = TextAlign.Center,
                    color = timeTextColor,
                    fontSize = timeTextSize
                )
                Text(
                    text = stringResource(id = R.string.h),
                    color = timeTextColor,
                    fontSize = timeUnitTextSize,
                    modifier = timeModifier
                )
                Text(
                    text = getMinutes(time.value),
                    color = timeTextColor,
                    fontSize = timeTextSize
                )
                Text(
                    text = stringResource(id = R.string.m),
                    color = timeTextColor,
                    fontSize = timeUnitTextSize,
                    modifier = timeModifier
                )
                Text(
                    text = getSeconds(time.value),
                    color = timeTextColor,
                    fontSize = timeTextSize
                )
                Text(
                    text = stringResource(id = R.string.s),
                    color = timeTextColor,
                    fontSize = timeUnitTextSize,
                    modifier = timeModifier
                )
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_input_delete),
                        contentDescription = "delete",
                        modifier = Modifier
                            .clickable {
                                if (time.value.isNotEmpty()) {
                                    time.value = time.value.substring(0, time.value.lastIndex)
                                }
                            },
                        alignment = Alignment.Center
                    )
                }
            }
            Divider()
            Column {
                val rowModifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DialPadText(ch = '1', onClick = { time.value = calculate(time.value, it) })
                    DialPadText(ch = '2', onClick = { time.value = calculate(time.value, it) })
                    DialPadText(ch = '3', onClick = { time.value = calculate(time.value, it) })
                }
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DialPadText(ch = '4', onClick = { time.value = calculate(time.value, it) })
                    DialPadText(ch = '5', onClick = { time.value = calculate(time.value, it) })
                    DialPadText(ch = '6', onClick = { time.value = calculate(time.value, it) })
                }
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DialPadText(ch = '7', onClick = { time.value = calculate(time.value, it) })
                    DialPadText(ch = '8', onClick = { time.value = calculate(time.value, it) })
                    DialPadText(ch = '9', onClick = { time.value = calculate(time.value, it) })
                }
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DialPadText(ch = '0', onClick = { time.value = calculate(time.value, it) })
                }
            }
            if (time.value.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            var seconds = 0
                            seconds += getSeconds(time.value).toInt()
                            seconds += getMinutes(time.value).toInt() * 60
                            seconds += getHours(time.value).toInt() * 60 * 60
                            Intent(context, CountDownActivity::class.java).apply {
                                putExtra("time", seconds)
                                context.startActivity(this)
                            }
                        }
                    ) {
                        Text(text = "Start")
                    }
                }
            }
        }
    }
}

@Composable
fun DialPadText(ch: Char, onClick: (Char) -> Unit) {
    Text(
        text = ch.toString(),
        modifier = Modifier
            .clickable { onClick(ch) }
            .padding(all = 10.dp)
            .requiredWidth(70.dp)
            .fillMaxHeight(),
        fontSize = 40.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
