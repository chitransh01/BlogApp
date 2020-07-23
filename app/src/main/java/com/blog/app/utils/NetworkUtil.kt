package com.blog.app.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil {
    companion object {
        fun isConnectedToWifi(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true
                }
            }
            return false
        }
    }
}