package com.example.navigation.util

import android.view.View

fun View.visibleOrNot(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}
