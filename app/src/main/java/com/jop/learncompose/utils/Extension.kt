package com.jop.learncompose.utils

import androidx.compose.ui.graphics.Color

fun String.toColor(): Color {
    // Remove the leading "#" if present
    val hexString = if (startsWith("#")) substring(1) else this

    // Ensure the hex string has the correct length (6 or 8 characters)
    require(hexString.length == 6 || hexString.length == 8) { "Invalid hex string: $this" }

    // Parse the hex string into integer values for red, green, blue, and alpha
    val colorInt = if (hexString.length == 6) {
        // 6-character hex (RGB)
        android.graphics.Color.parseColor("#$hexString")
    } else {
        // 8-character hex (ARGB)
        android.graphics.Color.parseColor(this)
    }

    return Color(colorInt)
}

fun Color.toHexCode(): String{
    val red = (red * 255).toInt()
    val green = (green * 255).toInt()
    val blue = (blue * 255).toInt()
    return String.format("#%02x%02x%02x", red, green, blue)
}