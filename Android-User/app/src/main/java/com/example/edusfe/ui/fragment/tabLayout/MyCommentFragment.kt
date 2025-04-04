package com.example.edusfe.ui.fragment.tabLayout

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edusfe.R
import com.example.edusfe.adapter.CommentkuAdapter
import com.example.edusfe.model.Comment
import com.example.edusfe.model.Thread
import com.example.edusfe.model.User
import com.example.edusfe.network.DatabaseConection
import com.example.edusfe.util.support
import java.io.IOException
import java.lang.Thread.State
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class MyCommentFragment : Fragment() {
    lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_comment, container, false)

        rv = view.findViewById(R.id.rv)
        showData(this).execute()

        return view
    }

    override fun onResume() {
        super.onResume()
        showData(this).execute()
    }

    inner class showData(
        private var fragment: MyCommentFragment
    ): AsyncTask<Void, Void, Void>() {
        var isDone = false
        var commentList: MutableList<Comment> = arrayListOf()
        override fun doInBackground(vararg p0: Void?): Void?{
            try {
                var connection: Connection = DatabaseConection().getConnection()
                if (connection != null) {
                    var query = "SELECT * FROM [Comment] WHERE user_Id = ${support.user_id} AND delete_at IS NULL"
                    var statement: Statement = connection.createStatement()
                    var resultSet: ResultSet = statement.executeQuery(query)


                    while (resultSet.next()) {
                        isDone = true
                        var user_id = resultSet.getInt("user_id")
                        var id = resultSet.getInt("id")
                        var thread_id = resultSet.getInt("thread_id")
                        var comment = resultSet.getString("comment")
                        var created_at = resultSet.getString("created_at")

                        var queryUser = "SELECT * FROM [User] WHERE id = $user_id"
                        var statementUser: Statement = connection.createStatement()
                        var resultSetUser: ResultSet = statementUser.executeQuery(queryUser)

                        if (resultSetUser.next()) {
                            commentList.add(Comment(
                                id,
                                Thread(thread_id, User(user_id, "", "", "", "", ""), "", "", "", "", "", ""),
                                User(user_id, "", "", "", "", ""),
                                comment,
                                created_at,
                                "",
                                ""
                            ))
                        }
                    }
                }
            } catch (e: Exception) {
                support.log(e.message.toString())
            } catch (e: IOException) {
                support.log(e.message.toString())
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            if (isDone == true) {
                fragment.context.run {
                    fragment.rv.adapter = CommentkuAdapter(fragment, commentList)
                    fragment.rv.layoutManager = LinearLayoutManager(fragment.context)
                }
            }
        }
    }

}