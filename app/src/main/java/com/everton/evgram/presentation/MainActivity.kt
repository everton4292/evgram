package com.everton.evgram.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.everton.evgram.R
import com.everton.evgram.util.FirebaseConfiguration
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarPrincipal.title = "Evgram"
        setSupportActionBar(toolbarPrincipal)

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


    private fun logoff() {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}