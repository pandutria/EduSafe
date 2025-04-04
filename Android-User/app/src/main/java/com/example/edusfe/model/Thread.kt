package com.example.edusfe.model


data class Thread(
    var id: Int,
    var user: User,
    var judul: String,
    var isi: String,
    var status: String,
    var created_at: String,
    var update_at: String = "",
    var delete_at: String
)
