package com.kazim.dictionaryapp.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kazim.dictionaryapp.R
import com.kazim.dictionaryapp.Word
import com.kazim.dictionaryapp.databinding.FragmentAddWordBinding


class AddWordFragment : Fragment() {
    var spinnerText:String = ""
    lateinit var binding: FragmentAddWordBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddWordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var database = FirebaseDatabase.getInstance().reference

        //Populate spinner -----------------------------------------------------------------
        val spinner: Spinner = binding.wordSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.words_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    spinnerText = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }
        //-----------------------------------------------------------------------------------

        //veri ekleme

        var user = FirebaseAuth.getInstance().currentUser
        var uid = user?.uid

        binding.btEkle.setOnClickListener{
            var category = spinnerText
            var word = binding.etWord.text.toString()
            var definition = binding.etDefinition.text.toString()
            var example = binding.etExampleSentence.text.toString()
            var synonym = binding.etSynonym.text.toString()
            var antonym = binding.etAntonym.text.toString()
            if (uid != null) {
                database.child("Personal Words").child(uid).child(word).setValue(Word(category,word,definition,example,synonym,antonym))
            }

            //TODO for adding database/stock words
            //database.child("Words").child(word).setValue(Word(category,word,definition,example,synonym,antonym))

            //clear text fields
            binding.etWord.text.clear()
            binding.etDefinition.text.clear()
            binding.etExampleSentence.text.clear()
            binding.etSynonym.text.clear()
            binding.etAntonym.text.clear()


        }


    }
    companion object {

        fun newInstance(): AddWordFragment {
            return AddWordFragment()
        }
    }
}