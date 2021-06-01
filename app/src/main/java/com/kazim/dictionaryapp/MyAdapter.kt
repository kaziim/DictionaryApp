package com.kazim.dictionaryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val wordList : ArrayList<Word>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = wordList[position]

        holder.word.text = currentItem.word
        holder.category.text = currentItem.category
        holder.definition.text = currentItem.definition
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val word : TextView = itemView.findViewById(R.id.tvWord)
        val category : TextView = itemView.findViewById(R.id.tvCategory)
        val definition : TextView = itemView.findViewById(R.id.tvDefinition)

    }
}