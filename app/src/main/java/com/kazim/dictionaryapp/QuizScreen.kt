package com.kazim.dictionaryapp

import android.content.Context
import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kazim.dictionaryapp.Adapters.WordAdapter
import com.kazim.dictionaryapp.databinding.ActivityQuizScreenBinding
import com.kazim.dictionaryapp.databinding.FragmentAddWordBinding
import java.lang.NullPointerException
import kotlin.random.Random

class QuizScreen : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var wordArrayList : ArrayList<Word>
    private lateinit var quizType:String
    private var questionNumber: Int = 0
    private var currentCorrect: Int = 0
    private var currentScore: Int = 0


    lateinit var tvQuestionNumber: TextView
    lateinit var tvQuestionText: TextView
    lateinit var tvStatus: TextView
    lateinit var bt1: Button
    lateinit var bt2: Button
    lateinit var bt3: Button
    lateinit var bt4: Button
    lateinit var btNextQuestion: ImageView
    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_screen)

        tvQuestionNumber = findViewById(R.id.tvQuestionNumber)
        tvQuestionText = findViewById(R.id.tvQuestionText)
        tvStatus = findViewById(R.id.tvStatus)
        bt1 = findViewById(R.id.button1)
        bt2 = findViewById(R.id.button2)
        bt3 = findViewById(R.id.button3)
        bt4 = findViewById(R.id.button4)
        btNextQuestion = findViewById(R.id.btNextQuestion)
        progressBar = findViewById(R.id.progressBar)

        wordArrayList = arrayListOf<Word>()

        getWordData()
        getQuizType()


        btNextQuestion.setOnClickListener{
            fillQuestion()
            resetButtons()
            enableButtons()
            setStatus(2)
        }

        bt1.setOnClickListener {
            if (currentCorrect==0){
                checkAnswers(currentCorrect)
                currentScore++
                setStatus(0)
            }else{
                checkAnswers(currentCorrect)
                setStatus(1)
            }
            disableButtons()
        }
        bt2.setOnClickListener {
            if (currentCorrect==1){
                checkAnswers(currentCorrect)
                currentScore++
                setStatus(0)
            }
            else{
                checkAnswers(currentCorrect)
                setStatus(1)
            }
            disableButtons()
        }
        bt3.setOnClickListener {
            if (currentCorrect==2){
                checkAnswers(currentCorrect)
                currentScore++
                setStatus(0)
            }
            else{
                checkAnswers(currentCorrect)
                setStatus(1)
            }
            disableButtons()
        }
        bt4.setOnClickListener {
            if (currentCorrect==3){
                checkAnswers(currentCorrect)
                currentScore++
                setStatus(0)
            }
            else{
                checkAnswers(currentCorrect)
                setStatus(1)
            }
            disableButtons()
        }

    }



    private fun fillQuestion() {
        if (questionNumber < wordArrayList.size){
            var randomNumber = Random.nextInt(0,4)
            Log.d("tag","${wordArrayList.size} size")

            var word:Word = Word() // placeholder

            try {
                word = wordArrayList[questionNumber++]
            }catch (e: IndexOutOfBoundsException){
                Toast.makeText(this,"An error occured, please try again!",Toast.LENGTH_LONG).show()
                goBackToMainMenu()
            }

            Log.d("tag","${word.category} vs $quizType")
            if (word.category == quizType){

                tvQuestionNumber.text = "Question $questionNumber"
                tvQuestionText.text = " Definition: ${word.definition} \n \n Synonyms: ${word.synonyms} \n Antonyms: ${word.antonyms}"
                fillOtherButtons(randomNumber,word)

            }
        }else{
            goBackToMainMenu()
        }

    }

    private fun goBackToMainMenu() {
        Toast.makeText(this,"You have completed the quiz!",Toast.LENGTH_LONG).show()
        val intent = Intent(this,MainMenu::class.java)
        intent.putExtra("score",currentScore)
        startActivity(intent)
    }

    private fun fillOtherButtons(randomNumber:Int, word: Word){

        if (randomNumber == 0){
            bt1.text = word.word
            currentCorrect = randomNumber
            bt2.text = getRandomQuizAnswer(word)
            bt3.text = getRandomQuizAnswer(word)
            bt4.text = getRandomQuizAnswer(word)
        }
        if (randomNumber == 1){
            bt2.text = word.word
            currentCorrect = randomNumber
            bt1.text = getRandomQuizAnswer(word)
            bt3.text = getRandomQuizAnswer(word)
            bt4.text = getRandomQuizAnswer(word)
        }
        if (randomNumber == 2){
            bt3.text = word.word
            currentCorrect = randomNumber
            bt2.text = getRandomQuizAnswer(word)
            bt1.text = getRandomQuizAnswer(word)
            bt4.text = getRandomQuizAnswer(word)
        }
        if (randomNumber == 3){
            bt4.text = word.word
            currentCorrect = randomNumber
            bt2.text = getRandomQuizAnswer(word)
            bt3.text = getRandomQuizAnswer(word)
            bt1.text = getRandomQuizAnswer(word)
        }
    }

    private fun getWordData() {
        progressBar.visibility = View.VISIBLE
        dbref = FirebaseDatabase.getInstance().getReference("Words")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (wordSnapshot in snapshot.children){
                        val word = wordSnapshot.getValue(Word::class.java)
                        if (word?.category == quizType){
                            wordArrayList.add(word!!)
                        }
                    }
                }
                progressBar.visibility = View.GONE
                wordArrayList.shuffle()

                fillQuestion()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getQuizType(){
        val bundle: Bundle? = intent.extras
        quizType = bundle?.getString("quiztype","Verb").toString()
        //Toast.makeText(this,quizType,Toast.LENGTH_SHORT).show()
    }

    private fun getRandomQuizAnswer(word: Word): String? {
        var string: String? = wordArrayList[Random.nextInt(0,wordArrayList.size)].word

        while (string.equals(word.word)){
            string = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
        }

        return string
    }

    private fun checkAnswers(correctAnswerIndex:Int){
        if (correctAnswerIndex == 0){
            bt1.setTextColor(Color.GREEN)
            bt2.setTextColor(Color.RED)
            bt3.setTextColor(Color.RED)
            bt4.setTextColor(Color.RED)
        }
        if (correctAnswerIndex == 1){
            bt1.setTextColor(Color.RED)
            bt2.setTextColor(Color.GREEN)
            bt3.setTextColor(Color.RED)
            bt4.setTextColor(Color.RED)
        }
        if (correctAnswerIndex == 2){
            bt1.setTextColor(Color.RED)
            bt2.setTextColor(Color.RED)
            bt3.setTextColor(Color.GREEN)
            bt4.setTextColor(Color.RED)
        }
        if (correctAnswerIndex == 3){
            bt1.setTextColor(Color.RED)
            bt2.setTextColor(Color.RED)
            bt3.setTextColor(Color.RED)
            bt4.setTextColor(Color.GREEN)
        }

    }

    private fun resetButtons() {
        bt1.setTextColor(Color.WHITE)
        bt2.setTextColor(Color.WHITE)
        bt3.setTextColor(Color.WHITE)
        bt4.setTextColor(Color.WHITE)
    }

    private fun disableButtons(){
        bt1.isEnabled = false
        bt2.isEnabled = false
        bt3.isEnabled = false
        bt4.isEnabled = false
        bt1.isClickable = false
        bt2.isClickable = false
        bt3.isClickable = false
        bt4.isClickable = false
    }

    private fun enableButtons(){
        bt1.isEnabled = true
        bt2.isEnabled = true
        bt3.isEnabled = true
        bt4.isEnabled = true
        bt1.isClickable = true
        bt2.isClickable = true
        bt3.isClickable = true
        bt4.isClickable = true
    }

    private fun setStatus(status:Int) {
        if(status == 0){ // correct choice
            tvStatus.text = "CORRECT CHOICE"
            tvStatus.setTextColor(Color.GREEN)
        }
        if (status == 1){ // wrong choice
            tvStatus.text = "WRONG CHOICE"
            tvStatus.setTextColor(Color.RED)
        }
        if (status == 2){ // reset status
            tvStatus.text = ""
        }
    }


}
