package com.everton.evgram.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.everton.evgram.R
import com.everton.evgram.presentation.fragments.FeedFragment
import com.everton.evgram.presentation.fragments.PostFragment
import com.everton.evgram.presentation.fragments.ProfileFragment
import com.everton.evgram.presentation.fragments.SearchFragment
import com.everton.evgram.util.FirebaseConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.bottom_navigation.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarPrincipal.title = "Evgram"
        setSupportActionBar(toolbarPrincipal)
        configureBottomNavigationView()
        habNavigation(bottomNavigation)

        firebaseAuth = FirebaseConfiguration().getFirebaseAuth()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflaterMenu = menuInflater
        inflaterMenu.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_quit) {
            logoff()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureBottomNavigationView() {
        val bottomNavigationItemViewEx = bottomNavigation
        bottomNavigationItemViewEx.enableAnimation(true)
        bottomNavigationItemViewEx.setTextVisibility(false)
        bottomNavigationItemViewEx.enableShiftingMode(1, false)
        bottomNavigationItemViewEx.enableItemShiftingMode(false)
    }

    private fun habNavigation(viewEx: BottomNavigationViewEx) {
        viewEx.setOnNavigationItemSelectedListener { item ->

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()


            when (item.itemId) {
                R.id.ic_home -> fragmentTransaction.replace(R.id.viewPager, FeedFragment())
                    .commit()
                R.id.ic_search -> fragmentTransaction.replace(R.id.viewPager, SearchFragment())
                    .commit()
                R.id.ic_post -> fragmentTransaction.replace(R.id.viewPager, PostFragment())
                    .commit()
                R.id.ic_profile -> fragmentTransaction.replace(R.id.viewPager, ProfileFragment())
                    .commit()
            }
            true
        }
    }

    private fun logoff() {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}