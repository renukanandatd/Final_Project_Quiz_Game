package com.example.final_project_quiz_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.final_project_quiz_game.databinding.ActivityQuizBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class QuizActivity : AppCompatActivity() {

    lateinit var quizBinding: ActivityQuizBinding
    val database = FirebaseDatabase.getInstance()
    val databaseReference = database.reference.child("Questions")

    var question = ""
    var optionA = ""
    var optionB = ""
    var optionC = ""
    var optionD = ""
    var correctAnswer = ""
    var questionCount = 0
    var questionNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding = ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        setContentView(view)

        gameLogic()

        quizBinding.buttonNext.setOnClickListener {
            gameLogic()
        }
        quizBinding.buttonFinish.setOnClickListener {

        }
        quizBinding.textViewOption1.setOnClickListener {

        }
        quizBinding.textViewOption2.setOnClickListener {

        }
        quizBinding.textViewOption3.setOnClickListener {

        }
        quizBinding.textViewOption4.setOnClickListener {

        }
    }

    private fun gameLogic(){
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                questionCount = snapshot.childrenCount.toInt()

                if(questionNumber<=questionCount){

                    question = snapshot.child(questionNumber.toString()).child("q").value.toString()
                    optionA = snapshot.child(questionNumber.toString()).child("a").value.toString()
                    optionB = snapshot.child(questionNumber.toString()).child("b").value.toString()
                    optionC = snapshot.child(questionNumber.toString()).child("c").value.toString()
                    optionD = snapshot.child(questionNumber.toString()).child("d").value.toString()
                    correctAnswer = snapshot.child(questionNumber.toString()).child("answer").value.toString()

                    quizBinding.textViewQuestion.text = question
                    quizBinding.textViewOption1.text = optionA
                    quizBinding.textViewOption2.text = optionB
                    quizBinding.textViewOption3.text = optionC
                    quizBinding.textViewOption4.text = optionD

                    quizBinding.progressBar.visibility = View.INVISIBLE
                    quizBinding.linearLayoutInfo.visibility = View.VISIBLE
                    quizBinding.linearLayoutQuestion.visibility = View.VISIBLE
                    quizBinding.linearLayoutButtons.visibility = View.VISIBLE

                }else{
                    Toast.makeText(applicationContext,"You answered all the questions",Toast.LENGTH_LONG).show()
                }
                questionNumber++
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_LONG).show()
            }

        })
    }
}