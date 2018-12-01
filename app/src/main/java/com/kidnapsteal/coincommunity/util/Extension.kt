package com.kidnapsteal.coincommunity.util

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.show(show: Boolean) {
    visibility = when (show) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}