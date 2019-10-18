package com.gemini.jalen.ligament_commons.util

import android.widget.TextView
import com.gemini.jalen.ligament.widget.Toast

fun message(msg: String) {
    Toast.show(msg)
}

fun TextView.getString(): String {
    return text.toString().trim()
}
