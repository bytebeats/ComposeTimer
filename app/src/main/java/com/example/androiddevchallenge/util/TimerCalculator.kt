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
package com.example.androiddevchallenge.util

fun getSeconds(time: String?): String {
    if (time.isNullOrEmpty()) {
        return "00"
    }
    if (time.length > 1) return time.takeLast(2)
    return "0$time"
}

fun getMinutes(time: String?): String {
    if (time.isNullOrEmpty()) {
        return "00"
    }
    if (getSeconds(time).startsWith("0")) return "00"
    if (time.length >= 4) return time.substring(time.length - 4, time.length - 2)
    return "0${time.first()}"
}

fun getHours(time: String?): String {
    if (time.isNullOrEmpty()) {
        return "00"
    }
    if (getMinutes(time).startsWith("0")) return "00"
    if (time.length >= 6) return time.substring(time.length - 6, time.length - 4)
    return "0${time.first()}"
}

fun calculate(time: String, d: Char): String {
    return if (time.isEmpty()) {
        if (d != '0') "$time$d"
        else time
    } else if (time.length >= 6) {
        time
    } else {
        "$time$d"
    }
}
