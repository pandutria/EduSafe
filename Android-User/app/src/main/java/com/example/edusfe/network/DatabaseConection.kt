package com.example.edusfe.network

import android.os.StrictMode
import android.util.Log
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager

class DatabaseConection {
    fun getConnection(): Connection {
        var ip = "10.0.2.2"
        var port = "1433"
        var user = "pandu"
        var password = "pandu123"
        var database = "EduSafe"
        var url = ""

        val policy =  StrictMode
            .ThreadPolicy
            .Builder()
            .permitAll()
            .build()

        StrictMode.setThreadPolicy(policy)

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            url = "jdbc:jtds:sqlserver://$ip:$port;databaseName=$database;user=$user;password=$password"
//            DriverManager.getConnection(url)
        } catch (e: Exception) {
            Log.d("Connection", "Eror ${e.message}")
        } catch (e: IOException) {
            Log.d("Connection", "Eror ${e.message}")
        }

        return DriverManager.getConnection(url)
    }
}