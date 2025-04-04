package com.example.edusfe.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.edusfe.R
import com.example.edusfe.model.Thread
import com.example.edusfe.ui.activity.KomentarActivity
import com.example.edusfe.util.support

class ThreadAdapter(private val threadList: List<Thread>): RecyclerView.Adapter<ThreadAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama = itemView.findViewById<TextView>(R.id.tvNama)
        var tvJudul = itemView.findViewById<TextView>(R.id.tvJudul)
        var tvisi = itemView.findViewById<TextView>(R.id.tvIsi)
        var tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var btnLihat = itemView.findViewById<Button>(R.id.btnLihatKomentar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_thread, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return threadList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thread = threadList[position]

        if (thread.user.nama == support.nama) {
            holder.tvNama.text = "Anda"
        } else {
            holder.tvNama.text = "${thread.user.nama} (Kelas ${thread.user.kelas})"
        }

        holder.tvJudul.text = "Judul : " + thread.judul
        holder.tvisi.text = "Thread : " + thread.isi

        if (thread.update_at != "") {
            holder.tvTanggal.text = "Tanggal :  " + thread.update_at + " (" + thread.status + ")"
        } else {
            holder.tvTanggal.text = "Tanggal :  " + thread.created_at + " (" + thread.status + ")"
        }

        if (thread.status == "aktif") {
            holder.btnLihat.visibility = View.VISIBLE
        } else {
            holder.btnLihat.visibility = View.GONE
        }

        holder.btnLihat.setOnClickListener {
            holder.itemView.context.startActivity(Intent(holder.itemView.context, KomentarActivity::class.java).apply {
                putExtra("thread_id", thread.id)
            })
        }

    }
}