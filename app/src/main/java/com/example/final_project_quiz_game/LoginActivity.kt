package com.example.final_project_quiz_game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.final_project_quiz_game.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding:ActivityLoginBinding
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        loginBinding.buttonSignIn.setOnClickListener {

            val email = loginBinding.textEmail.text.toString()
            val password = loginBinding.textPassword.text.toString()
            loginWithEmail(email,password)

        }
        loginBinding.googleSignIn.setOnClickListener {

        }
        loginBinding.textViewSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity,Sign_Up_Activity::class.java)
            startActivity(intent)
        }
        loginBinding.textViewForgotPassword.setOnClickListener {

        }
    }

    fun loginWithEmail(email: String,password: String){


    }
}