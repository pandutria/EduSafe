package com.example.edusfe.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.edusfe.R
import com.example.edusfe.adapter.ThreadAdapter.ViewHolder
import com.example.edusfe.model.Thread
import com.example.edusfe.network.DatabaseConection
import com.example.edusfe.ui.activity.EditThreadActivity
import com.example.edusfe.ui.fragment.tabLayout.MyThreadFragment
import com.example.edusfe.util.support
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class ThreadkuAdapter(private var fragment: MyThreadFragment, val threadList: List<Thread>): RecyclerView.Adapter<ThreadkuAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama = itemView.findViewById<TextView>(R.id.tvNama)
        var tvJudul = itemView.findViewById<TextView>(R.id.tvJudul)
        var tvisi = itemView.findViewById<TextView>(R.id.tvIsi)
        var tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var btnHapus= itemView.findViewById<Button>(R.id.btnHapus)
        var btnEdit = itemView.findViewById<Button>(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_mythread, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return threadList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thread = threadList[position]

//        if (thread.user.nama == support.nama) {
//            holder.tvNama.text = "Anda"
//        } else {
//            holder.tvNama.text = "${thread.user.nama} (Kelas ${thread.user.kelas})"
//        }

        holder.tvJudul.text = "Judul : " + thread.judul
        holder.tvisi.text = "Thread : " + thread.isi

        if (thread.update_at != "") {
            holder.tvTanggal.text = "Tanggal :  " + thread.update_at + " (" + thread.status + ")"
        } else {
            holder.tvTanggal.text = "Tanggal :  " + thread.created_at + " (" + thread.status + ")"
        }

        holder.btnHapus.setOnClickListener {
            deleteData(thread.id, fragment).execute()
        }

        holder.btnEdit.setOnClickListener {
            holder.itemView.context.startActivity(Intent(holder.itemView.context, EditThreadActivity::class.java).apply {
                putExtra("id", thread.id)
                putExtra("judul", thread.judul)
                putExtra("isi", thread.isi)
            })
        }
    }

    class deleteData(
        private var id: Int,
        private var fragment: MyThreadFragment
    ): AsyncTask<Void, Void, Void>() {
        var isDone = false
        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                var connection: Connection = DatabaseConection().getConnection()
                if (connection != null) {
                    var query = "UPDATE [Thread] SET deleted_at = CURRENT_TIMESTAMP WHERE id = $id"
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
            } else {
                support.msi(fragment.requireContext(), "Gagal menghapus")
            }

        }
    }
}