package com.example.edusfe.util

import android.content.Context
import android.util.Log
import android.widget.Toast

object support {
    var user_id = 0
    var username = ""
    var nama = ""
    var email = ""
    var password = ""
    var kelas = ""

    fun msi (context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun log (messege: String) {
        Log.d("SqlServer", "Eror : $messege")
    }
}