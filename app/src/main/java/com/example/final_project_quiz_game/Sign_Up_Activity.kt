package com.example.final_project_quiz_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.final_project_quiz_game.databinding.ActivityMainBinding
import com.example.final_project_quiz_game.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.firebase.auth.FirebaseAuth

class Sign_Up_Activity : AppCompatActivity() {

    lateinit var signUpBinding: ActivitySignUpBinding
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = signUpBinding.root
        setContentView(view)

        signUpBinding.buttonSignUp.setOnClickListener {

            val email = signUpBinding.textSignUpEmail.text.toString()
            val password = signUpBinding.textSignUpPassword.text.toString()
            signUpWithEmail(email,password)
        }
    }

    fun signUpWithEmail(email:String,password:String){

        signUpBinding.progressBarSignUp.visibility = View.VISIBLE
        signUpBinding.buttonSignUp.isClickable = false

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Your account has been created succesfully",Toast.LENGTH_LONG).show()
                finish()
                signUpBinding.progressBarSignUp.visibility = View.INVISIBLE
                signUpBinding.buttonSignUp.isClickable = true

            }else{
                Toast.makeText(applicationContext,task.exception?.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }
}