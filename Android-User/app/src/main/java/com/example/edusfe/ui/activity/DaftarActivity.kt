package com.example.edusfe.ui.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.edusfe.R
import com.example.edusfe.network.DatabaseConection
import com.example.edusfe.util.support
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class DaftarActivity : AppCompatActivity() {
    lateinit var etUsername: TextView
    lateinit var etNama: TextView
    lateinit var etEmail: TextView
    lateinit var etPassword: TextView
    lateinit var spinnerKelas: Spinner
    lateinit var btnDaftar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        etUsername = findViewById(R.id.etUsername)
        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        spinnerKelas = findViewById(R.id.spinnerKelas)
        btnDaftar = findViewById(R.id.btnDaftar)

        var list: MutableList<String> = arrayListOf()
        list.add("X")
        list.add("XI")
        list.add("XII")

        var spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKelas.adapter = spinnerAdapter

        btnDaftar.setOnClickListener {
            if (etUsername.text.toString() == "" || etNama.text.toString() == ""
                || etEmail.text.toString() == "" || etPassword.text.toString() == "") {
                support.msi(this, "All field must be filled")
                return@setOnClickListener
            }

            daftar(this,
                etUsername.text.toString(),
                etNama.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString(),
                spinnerKelas.selectedItem.toString()).execute()
        }

        findViewById<TextView>(R.id.tvDaftar).setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }


    }

    class daftar(
        private val activity: DaftarActivity,
        private val username: String,
        private val nama: String,
        private val email: String,
        private val password: String,
        private val kelas: String
    ) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            var connection: Connection?
            var preparedStatement: PreparedStatement?

            try {
                connection = DatabaseConection().getConnection()
                if (connection != null) {
                    var query = "INSERT INTO [User] (username, nama, email, password, kelas, created_at) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)"
                    preparedStatement = connection.prepareStatement(query)

                    preparedStatement.setString(1, username)
                    preparedStatement.setString(2, nama)
                    preparedStatement.setString(3, email)
                    preparedStatement.setString(4, password)
                    preparedStatement.setString(5, kelas)

                    var result = preparedStatement.executeUpdate()

                    if (result > 0) {
                        activity.runOnUiThread {
                            activity.startActivity(Intent(activity, LoginActivity::class.java))
                        }
                    } else {
                        activity.runOnUiThread {
                            support.msi(activity, "Ggal")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("SqlServer", "Eror : ${e.message}")
            }
            return null
        }
    }
}