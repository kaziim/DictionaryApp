package com.kazim.dictionaryapp.MainFragments

import RecyclerItemClickListener
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kazim.dictionaryapp.Adapters.WordAdapter
import com.kazim.dictionaryapp.Word
import com.kazim.dictionaryapp.databinding.FragmentExistingWordBinding


class ExistingWordFragment : Fragment() {
    private lateinit var dbref : DatabaseReference
    private lateinit var wordRecycylerView: RecyclerView
    private lateinit var wordArrayList : ArrayList<Word>

    lateinit var binding: FragmentExistingWordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentExistingWordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordRecycylerView = binding.wordRecyclerView
        wordRecycylerView.layoutManager = LinearLayoutManager(context)
        wordRecycylerView.setHasFixedSize(true)

        wordArrayList = arrayListOf<Word>()
        getWordData()



        wordRecycylerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), wordRecycylerView, object : RecyclerItemClickListener.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                Toast.makeText(requireContext(),"You need to long-press if you want to delete",Toast.LENGTH_LONG).show()
            }
            override fun onItemLongClick(view: View?, position: Int) {
                Log.d("tag","Clicked at position $position")
                Log.d("tag","Word at position ${wordArrayList[position].word}")
                var category = wordArrayList[position].category
                var word = wordArrayList[position].word
                var definition = wordArrayList[position].definition
                var example = wordArrayList[position].category
                var synonym = wordArrayList[position].synonyms
                var antonym = wordArrayList[position].antonyms
                addWord(category,word,definition,example,synonym,antonym)
            }
        }))

    }

    private fun addWord(category: String?, word: String?, definition: String?, example: String?, synonym: String?, antonym: String?) {
        var user = FirebaseAuth.getInstance().currentUser
        var uid = user?.uid

        var database = FirebaseDatabase.getInstance().reference

        uid?.let {
            it1 -> word?.let {
            database.child("Personal Words").child(it1).child(it).setValue(Word(category,word,definition,example,synonym,antonym))
            }
        }

        Toast.makeText(requireContext(),"$word has been added to your words!",Toast.LENGTH_LONG).show()
    }

    private fun getWordData() {
        binding.myWordsProgressBar.visibility = View.VISIBLE
        var user = FirebaseAuth.getInstance().currentUser
        var uid = user?.uid

        dbref = FirebaseDatabase.getInstance().getReference("Words")


        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (wordSnapshot in snapshot.children){
                        val word = wordSnapshot.getValue(Word::class.java)
                        wordArrayList.add(word!!)
                    }
                    wordRecycylerView.adapter = WordAdapter(wordArrayList)
                    binding.myWordsProgressBar.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    companion object {
        fun newInstance(): ExistingWordFragment {
            return ExistingWordFragment()
        }
    }
}