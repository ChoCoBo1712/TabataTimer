package com.example.tabatatimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import com.example.tabatatimer.repos.TimerRepository
import com.example.tabatatimer.viewmodels.TimerDetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.nio.file.Files.delete

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add -> {
            showAddPopup(findViewById(R.id.action_add))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showAddPopup(v: View) {
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.add_popup, popup.menu)
        popup.setOnMenuItemClickListener {
            onMenuItemClick(it)
        }
        popup.show()
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_timer -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, TimerDetailFragment.newInstance(0))
                    .addToBackStack(null)
                    .commit()
                true
            }
            R.id.add_sequence -> {
                //TODO: Add seq creation fragment
                true
            }
            else -> false
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.nav_sequences -> {
                //TODO
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_timers -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, TimerListFragment.newInstance())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_settings -> {
                //TODO
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                false
            }
        }
    }
}