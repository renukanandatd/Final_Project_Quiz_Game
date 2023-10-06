package com.example.final_project_quiz_game

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.final_project_quiz_game.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding:ActivityLoginBinding
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        val textOfGoogleButton = loginBinding.googleSignIn.getChildAt(0) as TextView
        textOfGoogleButton.text = "Continue with Google"
        textOfGoogleButton.setTextColor(Color.BLACK)
        textOfGoogleButton.textSize = 14F


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
            val intent = Intent(this@LoginActivity,ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    fun loginWithEmail(email: String,password: String){

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->

            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Welcome to Quiz Game!",Toast.LENGTH_LONG).show()
                val intent = Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_LONG).show()

            }
        }

    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        if(user!=null){
            Toast.makeText(applicationContext,"Welcome to Quiz Game!",Toast.LENGTH_LONG).show()
            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}