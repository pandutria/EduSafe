package com.example.edusfe.ui.activity

import android.os.AsyncTask
import android.os.Bundle
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

class EditThreadActivity : AppCompatActivity() {
    lateinit var etJudul: EditText
    lateinit var etIsi: EditText
    lateinit var btnEdit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_thread)

        etJudul = findViewById(R.id.etJudul)
        etIsi = findViewById(R.id.etIsi)
        btnEdit = findViewById(R.id.btnEdit)

        etJudul.setText("${intent.getStringExtra("judul")}")
        etIsi.setText("${intent.getStringExtra("isi")}")
        var id = intent.getIntExtra("id", 0)

        btnEdit.setOnClickListener {
            if (etJudul.text.toString() == "" || etIsi.text.toString() == "") {
                support.msi(this, "All fields must be filled")
                return@setOnClickListener
            }
            updateData(this, id, etJudul.text.toString(), etIsi.text.toString()).execute()
        }
        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
    }

    class updateData(
        private var activity: EditThreadActivity,
        private var id: Int,
        private var judul:String,
        var isi: String
    ) : AsyncTask<Void, Void, Void>() {
        var isDone = false
        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var connection: Connection = DatabaseConection().getConnection()
                if (connection != null) {
                    var query = "UPDATE [Thread] SET judul = '$judul', isi = '$isi', update_at = CURRENT_TIMESTAMP WHERE id = $id"
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
                activity.finish()
            } else {
                support.msi(activity, "Gagal edit data")
            }
        }
    }
}