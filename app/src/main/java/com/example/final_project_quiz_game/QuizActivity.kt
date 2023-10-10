package com.example.final_project_quiz_game

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.final_project_quiz_game.databinding.ActivityQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

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
    var questionNumber = 0

    var userAnswer = ""
    var answerCorrect = 0
    var answerWrong = 0

    lateinit var timer : CountDownTimer
    private val totalTime = 25000L
    var timerContinue = false
    var leftTime = totalTime

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val scoreRef = database.reference

    var questions = HashSet<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding = ActivityQuizBinding.inflate(layoutInflater)
        val view = quizBinding.root
        setContentView(view)

        do{

            val number = Random.nextInt(1,6)
            questions.add(number)

        }while(questions.size<4)

        gameLogic()

        quizBinding.buttonNext.setOnClickListener {
            resetTimer()
            gameLogic()
        }

        quizBinding.buttonFinish.setOnClickListener {
            sendScore()
        }

        quizBinding.textViewOption1.setOnClickListener {
            pauseTimer()
            userAnswer = "a"

            if(userAnswer==correctAnswer){
                quizBinding.textViewOption1.setBackgroundColor(Color.GREEN)
                answerCorrect++
                quizBinding.textViewCorrectAnswer.text = answerCorrect.toString()
            }

            else{
                quizBinding.textViewOption1.setBackgroundColor(Color.RED)
                answerWrong++
                quizBinding.textViewWrongAnswer.text = answerWrong.toString()
                findAnswer()
            }

            disableClickable()

        }

        quizBinding.textViewOption2.setOnClickListener {
            pauseTimer()
            userAnswer = "b"

            if(userAnswer==correctAnswer){
                quizBinding.textViewOption2.setBackgroundColor(Color.GREEN)
                answerCorrect++
                quizBinding.textViewCorrectAnswer.text = answerCorrect.toString()
            }

            else{
                quizBinding.textViewOption2.setBackgroundColor(Color.RED)
                answerWrong++
                quizBinding.textViewWrongAnswer.text = answerWrong.toString()
                findAnswer()
            }

            disableClickable()

        }

        quizBinding.textViewOption3.setOnClickListener {
            pauseTimer()
            userAnswer = "c"

            if(userAnswer==correctAnswer){
                quizBinding.textViewOption3.setBackgroundColor(Color.GREEN)
                answerCorrect++
                quizBinding.textViewCorrectAnswer.text = answerCorrect.toString()
            }

            else{
                quizBinding.textViewOption3.setBackgroundColor(Color.RED)
                answerWrong++
                quizBinding.textViewWrongAnswer.text = answerWrong.toString()
                findAnswer()
            }

            disableClickable()

        }

        quizBinding.textViewOption4.setOnClickListener {
            pauseTimer()
            userAnswer = "d"

            if(userAnswer==correctAnswer){
                quizBinding.textViewOption4.setBackgroundColor(Color.GREEN)
                answerCorrect++
                quizBinding.textViewCorrectAnswer.text = answerCorrect.toString()
            }

            else{
                quizBinding.textViewOption4.setBackgroundColor(Color.RED)
                answerWrong++
                quizBinding.textViewCorrectAnswer.text = answerWrong.toString()
                findAnswer()
            }

            disableClickable()

        }

    }

    private fun gameLogic(){

        restoreOptions()

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                questionCount = snapshot.childrenCount.toInt()

                if(questionNumber < questions.size){

                    question = snapshot.child(questions.elementAt(questionNumber).toString()).child("q").value.toString()
                    optionA = snapshot.child(questions.elementAt(questionNumber).toString()).child("a").value.toString()
                    optionB = snapshot.child(questions.elementAt(questionNumber).toString()).child("b").value.toString()
                    optionC = snapshot.child(questions.elementAt(questionNumber).toString()).child("c").value.toString()
                    optionD = snapshot.child(questions.elementAt(questionNumber).toString()).child("d").value.toString()
                    correctAnswer = snapshot.child(questions.elementAt(questionNumber).toString()).child("answer").value.toString()

                    quizBinding.textViewQuestion.text = question
                    quizBinding.textViewOption1.text = optionA
                    quizBinding.textViewOption2.text = optionB
                    quizBinding.textViewOption3.text = optionC
                    quizBinding.textViewOption4.text = optionD

                    quizBinding.progressBar.visibility = View.INVISIBLE
                    quizBinding.linearLayoutInfo.visibility = View.VISIBLE
                    quizBinding.linearLayoutQuestion.visibility = View.VISIBLE
                    quizBinding.linearLayoutButtons.visibility = View.VISIBLE

                    startTimer()

                }
                else{

                    val dialogMessage = AlertDialog.Builder(this@QuizActivity)
                    dialogMessage.setTitle("Quia Game")
                    dialogMessage.setMessage("Congratulations!!! \nYou have answered all the questions, do you want to see the results?")
                    dialogMessage.setCancelable(false)
                    dialogMessage.setPositiveButton("See Result"){dialogWindow,position ->

                        sendScore()

                    }

                    dialogMessage.setNegativeButton("Play Again"){dialogWindow,position ->

                        val intent = Intent(this@QuizActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }

                    dialogMessage.create().show()

                }

                questionNumber++

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_LONG).show()
            }

        })
    }
    fun findAnswer(){
        when(correctAnswer){
            "a" -> quizBinding.textViewOption1.setBackgroundColor(Color.GREEN)
            "b" -> quizBinding.textViewOption2.setBackgroundColor(Color.GREEN)
            "c" -> quizBinding.textViewOption3.setBackgroundColor(Color.GREEN)
            "d" -> quizBinding.textViewOption4.setBackgroundColor(Color.GREEN)
        }
    }
    fun disableClickable(){
        quizBinding.textViewOption1.isClickable = false
        quizBinding.textViewOption2.isClickable = false
        quizBinding.textViewOption3.isClickable = false
        quizBinding.textViewOption4.isClickable = false
    }

    fun restoreOptions(){
        quizBinding.textViewOption1.setBackgroundColor(Color.WHITE)
        quizBinding.textViewOption2.setBackgroundColor(Color.WHITE)
        quizBinding.textViewOption3.setBackgroundColor(Color.WHITE)
        quizBinding.textViewOption4.setBackgroundColor(Color.WHITE)

        quizBinding.textViewOption1.isClickable = true
        quizBinding.textViewOption2.isClickable = true
        quizBinding.textViewOption3.isClickable = true
        quizBinding.textViewOption4.isClickable = true
    }

    private fun startTimer(){
        timer = object : CountDownTimer(leftTime,1000){
            override fun onTick(millisUntilFinish: Long) {
                leftTime = millisUntilFinish
                updateCountdownText()
            }

            override fun onFinish() {
                disableClickable()
                resetTimer()
                updateCountdownText()
                quizBinding.textViewQuestion.text = "Sorry, time's up, please continue with next question"
                timerContinue = false
            }

        }.start()
        timerContinue = true
    }

    fun updateCountdownText(){
        var remainingTime : Int = (leftTime/1000).toInt()
        quizBinding.textViewTime.text = remainingTime.toString()
    }

    fun resetTimer(){
        pauseTimer()
        leftTime = totalTime
        updateCountdownText()
    }

    fun pauseTimer(){
        timer.cancel()
        timerContinue = false
    }

    fun sendScore(){

        user?.let {
            val userId = it.uid
            scoreRef.child("scores").child(userId).child("correct").setValue(answerCorrect)
            scoreRef.child("scores").child(userId).child("wrong").setValue(answerWrong).addOnSuccessListener {
                Toast.makeText(applicationContext,"Scores sent to database successfully",Toast.LENGTH_LONG).show()
                val intent = Intent(this@QuizActivity,ResultActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}