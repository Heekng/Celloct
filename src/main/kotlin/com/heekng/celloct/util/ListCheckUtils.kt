package com.heekng.celloct.util

fun <T> List<T>.whenEmpty(block: () -> Unit) {
    if (this.isEmpty()) {
        block()
    }
}

fun <T> List<T>.whenNotEmpty(block: () -> Unit) {
    if (this.isNotEmpty()) {
        block()
    }
}