package com.android.selfintroduction

import android.content.Context
import android.widget.Toast

object Toast {
    fun makeToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}