package com.kazim.dictionaryapp.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kazim.dictionaryapp.R
import com.kazim.dictionaryapp.databinding.FragmentQuizBinding
import com.kazim.dictionaryapp.databinding.FragmentSettingsBinding

class QuizFragment : Fragment() {

    lateinit var binding: FragmentQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



    companion object {
        fun newInstance(): QuizFragment {
            return QuizFragment()
        }
    }
}