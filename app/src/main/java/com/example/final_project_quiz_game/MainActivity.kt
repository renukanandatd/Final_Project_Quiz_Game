package com.example.final_project_quiz_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.final_project_quiz_game.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
    }
}