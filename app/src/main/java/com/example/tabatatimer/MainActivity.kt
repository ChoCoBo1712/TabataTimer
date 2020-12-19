package com.example.tabatatimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

//    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_timer_list) as TimerListFragment
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