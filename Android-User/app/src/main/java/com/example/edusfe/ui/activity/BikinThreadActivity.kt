package com.example.edusfe.ui.activity

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.edusfe.R
import com.example.edusfe.network.DatabaseConection
import com.example.edusfe.util.support
import java.sql.Connection
import java.sql.PreparedStatement

class BikinThreadActivity : AppCompatActivity() {
    lateinit var etJudul: EditText
    lateinit var etIsi: EditText
    lateinit var btnPosting: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_bikin_thread)

        etJudul = findViewById(R.id.etJudul)
        etIsi = findViewById(R.id.etIsi)
        btnPosting = findViewById(R.id.btnPosting)

        btnPosting.setOnClickListener {
            if (etJudul.text.toString() == "" || etIsi.text.toString() == "") {
                support.msi(this, "All field must be filled")
                return@setOnClickListener
            }

            sendData(this, etJudul.text.toString(), etIsi.text.toString()).execute()
        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
    }

    class sendData(
        private var activity: BikinThreadActivity,
        private var judul: String,
        private val isi: String
    ): AsyncTask<Void, Void, Void>() {
        var isDone = false
        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var connection: Connection = DatabaseConection().getConnection()

                if (connection != null) {
                    var query = "INSERT INTO [Thread] (user_id, judul, isi, status, created_at) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)"
                    var preparedStatement: PreparedStatement = connection.prepareStatement(query)

                    preparedStatement.setInt(1, support.user_id)
                    preparedStatement.setString(2, judul)
                    preparedStatement.setString(3, isi)
                    preparedStatement.setString(4, "aktif")

                    var result = preparedStatement.executeUpdate()

                    if (result > 0) {
                        isDone = true
                    }
                }
            } catch (e: Exception) {
                Log.d("SqlServer", "Eror : ${e.message}")
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            if (isDone ==true) {
                activity.finish()
            } else {
                support.msi(activity, "Gagal membuat thread baru")
            }
        }
    }
}