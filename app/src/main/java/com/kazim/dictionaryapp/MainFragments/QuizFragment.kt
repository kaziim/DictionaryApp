package com.kazim.dictionaryapp.MainFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.kazim.dictionaryapp.QuizScreen
import com.kazim.dictionaryapp.Word
import com.kazim.dictionaryapp.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    lateinit var binding: FragmentQuizBinding
    private var quizType:String ?= "verb"

    private lateinit var dbref : DatabaseReference
    private lateinit var wordArrayList : ArrayList<Word>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordArrayList = arrayListOf<Word>()

        val radioGroup: RadioGroup = binding.radioGroup
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                checkSwitches()
            }
        })

        binding.btStartQuiz.setOnClickListener {
            val intent = Intent(requireContext(),QuizScreen::class.java)
            intent.putExtra("quiztype",quizType)
            startActivity(intent)
        }

    }

    fun checkSwitches(){
        if (binding.rbVerbs.isChecked){
            quizType = "Verb"
            //Toast.makeText(requireContext(),quizType,Toast.LENGTH_SHORT).show()
        }
        if (binding.rbAdverbs.isChecked){
            quizType = "Adverb"
        }
        if (binding.rbAdjectives.isChecked){
            quizType = "Adjective"
        }
        if (binding.rbPhrases.isChecked){
            quizType = "Phrase or idiom"
        }

    }



    companion object {
        fun newInstance(): QuizFragment {
            return QuizFragment()
        }
    }
}