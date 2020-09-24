package com.everton.evgram.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.everton.evgram.R
import com.everton.evgram.model.User
import com.everton.evgram.util.FirebaseConfiguration
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.lang.Exception

class SignupActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        progressBar.visibility = View.GONE
        materialButtonCadastrar.setOnClickListener {
            if (validateFields()) {

                val user = User(
                    name = textInputUsuarioCadastro.text.toString()
                    , email = textInputEmailCadastro.text.toString(),
                    password = textInputSenhaCadastro.text.toString()
                )

                registerUser(user)
            }
        }

    }

    fun registerUser(user: User) {
        progressBar.visibility = View.VISIBLE
        firebaseAuth = FirebaseConfiguration().getFirebaseAuth()
        firebaseAuth.createUserWithEmailAndPassword(
            user.email,
            user.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Cadastro com sucesso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } else {
                var error = ""
                progressBar.visibility = View.GONE
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthWeakPasswordException) {
                    error = "Digite uma senha mais forte"
                } catch (e: FirebaseAuthInvalidCredentialsException){
                    error = "Digite um e-mail válido"
                } catch (e: FirebaseAuthUserCollisionException){
                    error = "Essa conta já foi cadastrada"
                } catch (e: Exception){
                    error = "ao cadastrar usuário, foi encontrado o erro: " + e.message
                    e.printStackTrace()
                }

                Toast.makeText(this, "Erro: $error", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun validateFields(): Boolean {
        if (textInputUsuarioCadastro.text!!.isEmpty()) {
            textInputUsuarioCadastro.error = "Favor inserir nome de usuário"
            textInputUsuarioCadastro.requestFocus()
            return false
        }
        if (textInputEmailCadastro.text!!.isEmpty()) {
            textInputEmailCadastro.error = "Favor inserir e-mail de cadastro"
            textInputEmailCadastro.requestFocus()
            return false
        }
        if (textInputSenhaCadastro.text!!.isEmpty()) {
            textInputSenhaCadastro.error = "Favor inserior senha de cadastro"
            textInputSenhaCadastro.requestFocus()
            return false
        }
        return true
    }
}