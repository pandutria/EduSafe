package com.example.edusfe.adapter

import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.edusfe.R
import com.example.edusfe.adapter.CommentAdapter.ViewHolder
import com.example.edusfe.model.Comment
import com.example.edusfe.network.DatabaseConection
import com.example.edusfe.ui.activity.EditCommentActivity
import com.example.edusfe.ui.activity.KomentarActivity
import com.example.edusfe.ui.fragment.tabLayout.MyCommentFragment
import com.example.edusfe.util.support
import java.sql.Connection
import java.sql.PreparedStatement

class CommentkuAdapter(private var fragment: MyCommentFragment, private var commentList: List<Comment>): RecyclerView.Adapter<CommentkuAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvNama = itemView.findViewById<TextView>(R.id.tvNama)
        var tvComment = itemView.findViewById<TextView>(R.id.tvComment)
        var tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var btnHapus= itemView.findViewById<Button>(R.id.btnHapus)
        var btnEdit = itemView.findViewById<Button>(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_mycomment, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var comment = commentList[position]
        holder.tvComment.text = "Comment : ${comment.comment}"
        holder.tvTanggal.text = "Tanggal :  " + comment.created_at

        holder.btnHapus.setOnClickListener {
            deleteData(fragment, comment.id).execute()
        }

        holder.btnEdit.setOnClickListener {
            holder.itemView.context.startActivity(Intent(holder.itemView.context, EditCommentActivity::class.java).apply {
                putExtra("id", comment.id)
                putExtra("comment", comment.comment)
            })
        }
    }

    class deleteData(
        private var fragment: MyCommentFragment,
        private var id: Int
    ) : AsyncTask<Void, Void, Void>() {
        var isDone = false
        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var connection: Connection = DatabaseConection().getConnection()
                if (connection != null) {
                    var query = "UPDATE [Comment] SET delete_at = CURRENT_TIMESTAMP WHERE id = $id"
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
                fragment.context.run {
                    fragment.showData(fragment).execute()
                }
            }
        }
    }

}