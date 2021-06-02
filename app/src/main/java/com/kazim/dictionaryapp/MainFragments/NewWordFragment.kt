package com.kazim.dictionaryapp.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kazim.dictionaryapp.R
import com.kazim.dictionaryapp.databinding.FragmentNewWordBinding


class NewWordFragment : Fragment() {

    lateinit var binding: FragmentNewWordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewWordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNewWord.setOnClickListener {
            findNavController().navigate(R.id.action_newWordFragment_to_addWordFragment)
        }
        binding.btExistingWord.setOnClickListener {
            findNavController().navigate(R.id.action_newWordFragment_to_existingWordFragment)
        }
    }

    companion object {

        fun newInstance(): NewWordFragment {
            return NewWordFragment()
        }

    }
}