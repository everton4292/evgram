package com.everton.evgram.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.everton.evgram.R
import com.everton.evgram.model.User
import com.everton.evgram.util.FirebaseConfiguration
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        verifyUserLogged()
        progressBarLogin.visibility = View.GONE
        textInputLoginEmail.requestFocus()

        materialButtonEntrar.setOnClickListener {
            if (validateFields()) {
                progressBarLogin.visibility = View.VISIBLE
                val emailLogin = textInputLayoutLoginEmail.editText?.text.toString()
                val senhaLogin = textInputLayoutLoginSenha.editText?.text.toString()
                val userLogin = User(email = emailLogin, password = senhaLogin)
                startLogin(userLogin)
            }
        }

        materialTextViewSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    fun startLogin(user: User) {
        firebaseAuth = FirebaseConfiguration().getFirebaseAuth()
        firebaseAuth.signInWithEmailAndPassword(
            user.email,
            user.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progressBarLogin.visibility = View.GONE
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Erro ao fazer login", Toast.LENGTH_SHORT).show()
                progressBarLogin.visibility = View.GONE
            }
        }
    }

    private fun verifyUserLogged(){
        firebaseAuth = FirebaseConfiguration().getFirebaseAuth()
        if(firebaseAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun validateFields(): Boolean {
        if (textInputLoginEmail.text!!.isEmpty()) {
            textInputLoginEmail.error = "Favor inserir e-mail"
            textInputLoginEmail.requestFocus()
            return false
        }
        if (textInputLoginSenha.text!!.isEmpty()) {
            textInputLoginSenha.error = "Favor inserir senha"
            textInputLoginSenha.requestFocus()
            return false
        }
        return true
    }
}