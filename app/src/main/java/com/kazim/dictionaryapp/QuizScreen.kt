package com.kazim.dictionaryapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kazim.dictionaryapp.Adapters.WordAdapter
import com.kazim.dictionaryapp.databinding.ActivityQuizScreenBinding
import com.kazim.dictionaryapp.databinding.FragmentAddWordBinding
import kotlin.random.Random

class QuizScreen : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var wordArrayList : ArrayList<Word>
    private lateinit var quizType:String
    private var questionNumber: Int = 0

    lateinit var tvQuestionNumber: TextView
    lateinit var tvQuestionText: TextView
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
        }

    }

    private fun fillQuestion() {
        var randomNumber = Random.nextInt(0,4)
        Log.d("tag","${wordArrayList.size} size")
        var word:Word = wordArrayList[questionNumber++]
        if (questionNumber==10){
            goBackToMainMenu()
        }
        Log.d("tag","${word.category} vs $quizType")
        if (word.category == quizType){
            tvQuestionNumber.text = "Question $questionNumber"
            tvQuestionText.text = " Definition: ${word.definition} \n \n Synonyms: ${word.synonyms} \n Antonyms: ${word.antonyms}"
            fillOtherButtons(randomNumber,word)

        }
    }

    private fun goBackToMainMenu() {
        Toast.makeText(this,"You have completed the quiz!",Toast.LENGTH_LONG).show()
        val intent = Intent(this,MainMenu::class.java)
        startActivity(intent)
    }

    private fun fillOtherButtons(randomNumber:Int, word: Word){

        if (randomNumber == 0){
            bt1.text = word.word
            bt2.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
            bt3.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
            bt4.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
        }
        if (randomNumber == 1){
            bt2.text = word.word
            bt1.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
            bt3.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
            bt4.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
        }
        if (randomNumber == 2){
            bt3.text = word.word
            bt2.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
            bt1.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
            bt4.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
        }
        if (randomNumber == 3){
            bt4.text = word.word
            bt2.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
            bt3.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
            bt1.text = wordArrayList[Random.nextInt(0,wordArrayList.size)].word
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
                fillQuestion()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getQuizType(){
        val bundle: Bundle? = intent.extras
        quizType = bundle?.getString("quiztype").toString()

        //Toast.makeText(this,quizType,Toast.LENGTH_SHORT).show()
    }


}
