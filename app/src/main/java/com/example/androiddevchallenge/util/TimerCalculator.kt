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
    return if (time.isNullOrEmpty()) {
        "00"
    } else if (time.length > 1) time.takeLast(2) else {
        "0$time"
    }
}

fun getMinutes(time: String?): String {
    return if (time.isNullOrEmpty()) {
        "00"
    } else if (time.length < 3) {
        "00"
    } else if (time.length >= 4) {
        time.substring(time.length - 4, time.length - 2)
    } else {
        "0${time.first()}"
    }
}

fun getHours(time: String?): String {
    return if (time.isNullOrEmpty()) {
        "00"
    } else if (time.length < 5) {
        "00"
    } else if (time.length >= 6) {
        time.substring(time.length - 6, time.length - 4)
    } else {
        "0${time.first()}"
    }
}

fun calculate(time: String, d: Char): String {
    return if (time.isEmpty()) {
        if (d != '0') "$d"
        else time
    } else if (time.length >= 6) {
        time
    } else {
        "$time$d"
    }
}

fun getFullTime(seconds: Int): String {
    val s = seconds % 100
    val m = seconds / 100 % 100
    val h = seconds / 10000 % 100
    return if (h > 0) {
        "${if (h < 10) "0$h" else h.toString()}:${if (m < 10) "0$m" else m.toString()}:${if (s < 10) "0$s" else s.toString()}"
    } else if (m > 0) {
        "${if (m < 10) "0$m" else m.toString()}:${if (s < 10) "0$s" else s.toString()}"
    } else {
        if (s < 10) "0$s" else s.toString()
    }
}
