package com.kazim.dictionaryapp.MainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kazim.dictionaryapp.Adapters.WordAdapter
import com.kazim.dictionaryapp.Word
import com.kazim.dictionaryapp.databinding.FragmentMyWordsBinding

class MyWordsFragment : Fragment() {

    private lateinit var dbref :DatabaseReference
    private lateinit var wordRecycylerView: RecyclerView
    private lateinit var wordArrayList : ArrayList<Word>

    lateinit var binding: FragmentMyWordsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyWordsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordRecycylerView = binding.wordRecyclerView
        wordRecycylerView.layoutManager = LinearLayoutManager(context)
        wordRecycylerView.setHasFixedSize(true)

        wordArrayList = arrayListOf<Word>()
        getWordData()


    }

    private fun getWordData() {
        binding.myWordsProgressBar.visibility = View.VISIBLE
        var user = FirebaseAuth.getInstance().currentUser
        var uid = user?.uid

        dbref = uid?.let { FirebaseDatabase.getInstance().getReference("Personal Words").child(it) }!!


        dbref.addValueEventListener(object : ValueEventListener{
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
        fun newInstance(): MyWordsFragment {
            return MyWordsFragment()
        }
    }
}