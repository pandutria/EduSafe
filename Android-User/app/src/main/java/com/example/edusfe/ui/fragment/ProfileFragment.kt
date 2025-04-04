package com.example.edusfe.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.edusfe.R
import com.example.edusfe.adapter.ViewPagerAdapter
import com.example.edusfe.ui.activity.EditProfileActivity
import com.example.edusfe.util.support
import com.google.android.material.tabs.TabLayout

class ProfileFragment : Fragment() {
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<Button>(R.id.btnEdit).setOnClickListener {
            startActivity(Intent(context, EditProfileActivity::class.java))
        }

        view.findViewById<TextView>(R.id.tvNama).text = support.nama
        view.findViewById<TextView>(R.id.tvEmail).text = support.email

        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)


        return view
    }

}