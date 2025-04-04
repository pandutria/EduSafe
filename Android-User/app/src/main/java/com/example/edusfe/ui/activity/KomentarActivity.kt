package com.example.edusfe.ui.activity

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edusfe.R
import com.example.edusfe.adapter.CommentAdapter
import com.example.edusfe.model.Comment
import com.example.edusfe.model.Thread
import com.example.edusfe.model.User
import com.example.edusfe.network.DatabaseConection
import com.example.edusfe.util.support
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class KomentarActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView
    lateinit var etKomentar: EditText
    lateinit var btnKomentar: Button
    lateinit var layoutKomentar: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_komentar)

        rv = findViewById(R.id.rv)
        etKomentar = findViewById(R.id.etKomentar)
        btnKomentar = findViewById(R.id.btnBerikanKomentar)
        layoutKomentar = findViewById(R.id.layoutKomentar)

        var thread_id = intent.getIntExtra("thread_id", 0)
        showData(this, rv, thread_id).execute()

        btnKomentar.setOnClickListener {
            if (etKomentar.text.toString() == "") {
                support.msi(this, "Field must be filled")
                return@setOnClickListener
            }

            sendData(etKomentar.text.toString(), thread_id).execute()
            showData(this, rv, thread_id).execute()
            etKomentar.text.clear()
        }

        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
    }

    class sendData(
        private val comment: String,
        private val thread_id: Int
    ) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var connection: Connection = DatabaseConection().getConnection()
                if (connection != null) {
                    var query = "INSERT INTO [Comment] (thread_Id, comment, user_Id, created_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)"
                    var preparedStatement: PreparedStatement = connection.prepareStatement(query)

                    preparedStatement.setInt(1, thread_id)
                    preparedStatement.setString(2, comment)
                    preparedStatement.setInt(3, support.user_id)

                    preparedStatement.executeUpdate()
                }
            } catch (e: Exception) {
                support.log(e.message.toString())
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
//            if (isDone == true) {
//                showData().execute()
//            }
        }
    }

    class showData(
        private var context: KomentarActivity,
        private val rv: RecyclerView,
        private val thread_id: Int) : AsyncTask<Void, Void, Void>() {

        var commentList: MutableList<Comment> = arrayListOf()

        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var connection: Connection = DatabaseConection().getConnection()
                if (connection != null) {
                    var query = "SELECT * FROM [Comment] WHERE thread_Id = $thread_id AND delete_at IS NULL"
                    var statement: Statement = connection.createStatement()
                    var resultSet: ResultSet = statement.executeQuery(query)

                    while (resultSet.next()) {
                        var comment = resultSet.getString("comment")

                        var user_id = resultSet.getInt("user_id")
                        var queryUser = "SELECT * FROM [User] WHERE id = $user_id"
                        var statementUser: Statement = connection.createStatement()
                        var resultSetUser: ResultSet = statementUser.executeQuery(queryUser)

                        while (resultSetUser.next()) {
                            var nama = resultSetUser.getString("nama")
                            var kelas = resultSetUser.getString("kelas")
                            var tanggal = resultSetUser.getString("created_at")

                            commentList.add(Comment(
                                0,
                                null,
                                User(user_id, "", nama, "", "", kelas),
                                comment,
                                tanggal,
                                "",
                                "",

                            ))
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("SqlServer", "Eror ${e.message}")
            }
            return null

        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            rv.adapter = CommentAdapter(context, commentList)
            rv.layoutManager = LinearLayoutManager(context)
        }

    }
}