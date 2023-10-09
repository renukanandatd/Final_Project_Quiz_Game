package com.example.final_project_quiz_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.final_project_quiz_game.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    lateinit var quizBinding: ActivityQuizBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding = ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        setContentView(view)


    }
}