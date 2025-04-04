package com.example.edusfe.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edusfe.R
import com.example.edusfe.adapter.ThreadAdapter
import com.example.edusfe.model.Thread
import com.example.edusfe.model.User
import com.example.edusfe.network.DatabaseConection
import com.example.edusfe.ui.activity.BikinThreadActivity
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class BerandaFragment : Fragment() {
    lateinit var rv: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_beranda, container, false)

        rv = view.findViewById(R.id.rv)
        showData(context as Activity, rv).execute()

        view.findViewById<Button>(R.id.btnBuat).setOnClickListener {
            startActivity(Intent(context,BikinThreadActivity::class.java))
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        showData(context as Activity, rv).execute()
    }

    class showData(private val context: Context, val rv: RecyclerView) :
        AsyncTask<Void, Void, Void>() {
        var threadList: MutableList<Thread> = arrayListOf()
        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var connection: Connection = DatabaseConection().getConnection()
                if (connection != null) {
                    var query = "SELECT * FROM [Thread] WHERE deleted_at IS NULL"
                    var statement: Statement = connection.createStatement()
                    var resultSet: ResultSet = statement.executeQuery(query)

                    while (resultSet.next()) {
                        var id = resultSet.getInt("id")
                        var judul = resultSet.getString("judul")
                        var isi = resultSet.getString("isi")
                        var created_at = resultSet.getString("created_at")
                        var update_at = resultSet.getString("update_at")
                        var status = resultSet.getString("status")
                        var user_id = resultSet.getString("user_id")

                        var queryUser = "SELECT * FROM [User] WHERE id = $user_id"
                        var statementUser: Statement = connection.createStatement()
                        var resultSetUser: ResultSet = statementUser.executeQuery(queryUser)

                        if (resultSetUser.next()) {
                            var nama = resultSetUser.getString("nama")
                            var kelas = resultSetUser.getString("kelas")

                            threadList.add(
                                Thread(
                                    id,
                                    User(0, "", nama, "", "", kelas),
                                    judul,
                                    isi,
                                    status,
                                    created_at,
                                    "",
                                    ""
                                )
                            )
                        }
                    }
//                    fragment.context.run {
//                        rv.adapter = ThreadAdapter(threadList)
//                        rv.layoutManager = LinearLayoutManager(this)
//                    }
                }

            } catch (e: Exception) {
                Log.d("SqlServer", "Eror : ${e.message}")
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            rv.adapter = ThreadAdapter(threadList)
            rv.layoutManager = LinearLayoutManager(context)
        }
    }

}