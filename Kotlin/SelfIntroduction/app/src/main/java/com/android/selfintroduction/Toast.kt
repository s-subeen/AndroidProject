package com.android.selfintroduction

import android.content.Context
import android.widget.Toast

object Toast {
    fun Context.makeToast(text: String) =
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}