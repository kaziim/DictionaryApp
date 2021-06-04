package com.kazim.dictionaryapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kazim.dictionaryapp.R
import com.kazim.dictionaryapp.Word

class WordAdapter(private val wordList : ArrayList<Word>): RecyclerView.Adapter<WordAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = wordList[position]

        holder.word.text = currentItem.word
        holder.category.text = currentItem.category
        holder.definition.text = currentItem.definition
        holder.synonym.text = currentItem.synonyms
        holder.antonym.text = currentItem.antonyms

    }


    override fun getItemCount(): Int {
        return wordList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val word : TextView = itemView.findViewById(R.id.tvWord)
        val category : TextView = itemView.findViewById(R.id.tvCategory)
        val definition : TextView = itemView.findViewById(R.id.tvDefinition)
        val synonym : TextView = itemView.findViewById(R.id.tvSynonym)
        val antonym : TextView = itemView.findViewById(R.id.tvAntonym)



    }

    fun deleteItem(i:Int){
        wordList.removeAt(i)
        notifyDataSetChanged()
    }


}