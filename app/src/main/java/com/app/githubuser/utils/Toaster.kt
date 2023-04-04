package com.app.githubuser.utils

import android.content.Context
import android.widget.Toast

object Toaster {
    fun short(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun long(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}