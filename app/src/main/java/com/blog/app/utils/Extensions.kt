package com.blog.app.utils

import com.blog.app.constants.Constants
import java.text.SimpleDateFormat
import java.util.*

fun String.getTimeDiff(): String {
    val sdf = SimpleDateFormat(Constants.UTC_DATE_FORMAT, Locale.US)
    val parsedDate = sdf.parse(this)
    val curDate = Date(System.currentTimeMillis())
    var result = ""
    if (parsedDate != null) {
        val diff = curDate.time - parsedDate.time
        val diffSeconds = diff / 1000 % 60
        val diffMinutes = diff / (60 * 1000) % 60
        val diffHours = diff / (60 * 60 * 1000)
        val diffInDays = diff / (1000 * 60 * 60 * 24)
        if (diffInDays > 1) {
            result = diffInDays.toString() + Constants.DAY
            return result
        } else if (diffHours < 24) {
            result = diffHours.toString() + Constants.HOUR
            return result
        } else if (diffMinutes >= 1) {
            result = diffMinutes.toString() + Constants.MIN
            return result
        } else if (diffSeconds < 60) {
            result = diffSeconds.toString() + Constants.SEC
            return result
        }
    }
    return ""
}

fun Int.getValue(): String {
    var value = this.toDouble()
    if (value > 1000) {
        value /= 1000
    }
    return "%.2f".format(value) + "K"
}