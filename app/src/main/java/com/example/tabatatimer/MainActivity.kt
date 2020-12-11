package com.example.tabatatimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

//    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentList) as ListFragment
//        when(item.itemId) {
//            R.id.nav_sequences -> {
//                fragment.changeList(R.array.length)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.nav_timers -> {
//                fragment.changeList(R.array.weight)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.nav_settings -> {
//                fragment.changeList(R.array.volume)
//                return@OnNavigationItemSelectedListener true
//            }
//            else -> {
//                false
//            }
//        }
//    }
}