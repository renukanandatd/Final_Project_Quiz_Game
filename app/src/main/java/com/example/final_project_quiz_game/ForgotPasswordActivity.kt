package com.example.final_project_quiz_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.final_project_quiz_game.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var forgotPasswordBinding: ActivityForgotPasswordBinding
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotPasswordBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = forgotPasswordBinding.root
        setContentView(view)

        forgotPasswordBinding.buttonSignUp.setOnClickListener {

            val email = forgotPasswordBinding.textSignUpEmail.text.toString()

            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->

                if(task.isSuccessful){
                    Toast.makeText(applicationContext,"Reset password mail has been sent to your email",Toast.LENGTH_LONG).show()
                    finish()
                }
                else{
                    Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}