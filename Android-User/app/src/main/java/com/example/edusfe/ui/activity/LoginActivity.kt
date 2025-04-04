package com.example.edusfe.ui.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.edusfe.R
import com.example.edusfe.network.DatabaseConection
import com.example.edusfe.util.support
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class LoginActivity : AppCompatActivity() {
    lateinit var tvDaftar: TextView
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var btnMasuk:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnMasuk = findViewById(R.id.btnMasuk)

        tvDaftar = findViewById(R.id.tvDaftar)
        tvDaftar.setOnClickListener {
            startActivity(Intent(this, DaftarActivity::class.java))
        }

        etUsername.setText("pandu")
//        etPassword.setText("pandu123")

        btnMasuk.setOnClickListener {
            if (etUsername.text.toString() == "" || etPassword.text.toString() == "") {
                support.msi(this, "All fields must be filled");
                return@setOnClickListener
            }

            login(this, etUsername.text.toString(), etPassword.text.toString()).execute()
        }
    }

    class login(private val activity: LoginActivity,
                private val username: String,
                private val password: String
    ) : AsyncTask<Void, Void, Void>() {

        var isLogin = false
        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var connection: Connection? = DatabaseConection().getConnection()
                if (connection!= null) {
                    var query = "SELECT * FROM [User] WHERE username = '$username' and password = '$password'"
                    var statement: Statement = connection.createStatement()
                    var resultSet: ResultSet = statement.executeQuery(query)

                    if (resultSet.next()) {
                        support.user_id = resultSet.getInt("id")
                        support.nama = resultSet.getString("nama")
                        support.email = resultSet.getString("email")
                        support.username = resultSet.getString("username")
                        support.password = resultSet.getString("password")
                        support.kelas = resultSet.getString("kelas")
                        isLogin = true
//                        Log.d("SqlServer", "Eror : ${support.user_id}")
//                        activity.startActivity(Intent(activity, MainActivity::class.java))

                    }
//                    else {
//                        activity.runOnUiThread {
//                            support.msi(activity, "Username/password salah!!")
//                        }
//                    }
                }
            } catch (e: Exception) {
                Log.d("SqlServer", "Eror : ${e.message}")
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            if (isLogin == true) {
                activity.startActivity(Intent(activity, MainActivity::class.java))
            } else {
                support.msi(activity, "Username/password salah")
            }
        }
    }
}