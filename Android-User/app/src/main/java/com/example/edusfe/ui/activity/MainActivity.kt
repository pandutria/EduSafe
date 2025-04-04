package com.example.edusfe.ui.activity

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.edusfe.R
import com.example.edusfe.network.DatabaseConection
import com.example.edusfe.ui.fragment.BerandaFragment
import com.example.edusfe.ui.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNav)
        showFragment(BerandaFragment())

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_beranda -> {
                    showFragment(BerandaFragment())
                    return@setOnNavigationItemSelectedListener  true
                }

                R.id.menu_profile -> {
                    showFragment(ProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

//    class fecthData(private val activity: MainActivity) : AsyncTask<Void, Void, Void>() {
//        override fun doInBackground(vararg p0: Void?): Void? {
//            var connection: Connection?
//            var statement: Statement?
//            var resultSet: ResultSet?
//
//            try {
//                connection = DatabaseConection().getConnection()
//                if (connection != null) {
//                    var query = "SELECT * FROM Thread WHERE id = 3"
//                    statement = connection.createStatement()
//                    resultSet = statement.executeQuery(query)
//
//                    if (resultSet.next()) {
//                        val judul = resultSet.getString("judul")
//
////                        activity.runOnUiThread {
////                            activity.tvJudul.text = judul
////                        }
//                    }
//                }
//            } catch (e: Exception) {
//                Log.d("Fecth", "Eror : ${e.message}")
//            }
//
//            return null
//        }
//    }
}