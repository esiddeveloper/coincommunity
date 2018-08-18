package com.kidnapsteal.coincommunity.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun getShortTime(timeStamp: Long): String {
        val date = SimpleDateFormat("hh:mm", Locale("en")).format(Date(timeStamp))
        return date.toString()
    }

}