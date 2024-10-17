package com.example.worktestcomposeproject.util.extensions

import android.content.Context

fun loadJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}