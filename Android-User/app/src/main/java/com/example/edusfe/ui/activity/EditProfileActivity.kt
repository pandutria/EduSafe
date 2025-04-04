package com.example.edusfe.ui.activity

import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.edusfe.R
import com.example.edusfe.network.DatabaseConection
import com.example.edusfe.util.support
import kotlinx.coroutines.flow.merge
import java.sql.Connection
import java.sql.PreparedStatement

class EditProfileActivity : AppCompatActivity() {
    lateinit var etUsername: TextView
    lateinit var etNama: TextView
    lateinit var etEmail: TextView
    lateinit var etPassword: TextView
    lateinit var spinnerKelas: Spinner
    lateinit var btnEdit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }

        etUsername = findViewById(R.id.etUsername)
        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        spinnerKelas = findViewById(R.id.spinnerKelas)
        btnEdit = findViewById(R.id.btnEdit)

        etUsername.setText("${support.username}")
        etNama.setText("${support.nama}")
        etEmail.setText("${support.email}")
        etPassword.setText("${support.password}")

        var list: MutableList<String> = arrayListOf()
        list.add("X")
        list.add("XI")
        list.add("XII")

        var spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKelas.adapter = spinnerAdapter

        btnEdit.setOnClickListener {
            if (etUsername.text.toString() == "" || etNama.text.toString() == ""
                || etEmail.text.toString() == "" || etPassword.text.toString() == "") {
                support.msi(this, "All field must be filled")
                return@setOnClickListener
            }
            updateData(
                this,
                etUsername.text.toString(),
                etNama.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString(),
                spinnerKelas.selectedItem.toString()).execute()

        }
    }

    class updateData(
        private var activity: EditProfileActivity,
        private var username: String,
        private var nama: String,
        private var email: String,
        private var password: String,
        private var kelas: String
    ) : AsyncTask<Void, Void, Void>() {
        var isDone = false
        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var connection: Connection = DatabaseConection().getConnection()
                if (connection != null) {
                    var query = "UPDATE [User] SET username = '$username', nama = '$nama', email = '$email', password = '$password', kelas = '$kelas' WHERE id = ${support.user_id}"
                    var preparedStatement: PreparedStatement = connection.prepareStatement(query)

                    var result = preparedStatement.executeUpdate()

                    if (result > 0) {
                        isDone = true
                    }
                }
            } catch (e: Exception) {
                support.log(e.message.toString())
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            if (isDone == true) {
                activity.runOnUiThread {
                    activity.finish()
                }
            } else {
                support.msi(activity,"Gagal ubah data")
            }
        }
    }
}