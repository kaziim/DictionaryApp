package com.kazim.dictionaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kazim.dictionaryapp.databinding.ActivityMainBinding
import java.lang.StringBuilder

class AddWord : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var spinnerText:String = ""
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var database = FirebaseDatabase.getInstance().reference

        //Populate spinner -----------------------------------------------------------------
        val spinner: Spinner = findViewById(R.id.wordSpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.words_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = this
        }
        //-----------------------------------------------------------------------------------

        //veri ekleme
        binding.btEkle.setOnClickListener{
            var category = spinnerText
            var word = binding.etWord.text.toString()
            var definition = binding.etDefinition.text.toString()
            var example = binding.etExampleSentence.text.toString()
            var synonym = binding.etSynonym.text.toString()
            var antonym = binding.etAntonym.text.toString()
            database.child(category).child(word).setValue(Word(category,word,definition,example,synonym,antonym))

            //clear text fields
            binding.etWord.text.clear()
            binding.etDefinition.text.clear()
            binding.etExampleSentence.text.clear()
            binding.etSynonym.text.clear()
            binding.etAntonym.text.clear()


        }

        // veri okuma işlemi
        /*var getdata = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder() // metin birleştirme sınıfı
                for (i in snapshot.children){
                    var adsoyad = i.child("padsoyad").getValue()
                    var maas = i.child("pmaas").getValue()
                    sb.append("${i.key} $adsoyad $$maas \n")
                }
                binding.tvSonuc.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }*/

       /* database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)*/


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        spinnerText = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}