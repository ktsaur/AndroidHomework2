package ru.itis.second_sem.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi

fun timestamp() {
    println(System.currentTimeMillis())
}

fun main() {
    timestamp()
}