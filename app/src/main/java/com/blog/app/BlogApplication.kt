package com.blog.app

import android.app.Application
import android.content.Context

class BlogApplication : Application() {
    companion object {
        private var mInstance: BlogApplication? = null

        /**
         * This method will return the instance of the application class
         *
         * @param context
         */
        fun getInstance(context: Context): BlogApplication {
            if (mInstance == null) {
                mInstance = context.applicationContext as BlogApplication
            }
            return mInstance as BlogApplication
        }
    }
}