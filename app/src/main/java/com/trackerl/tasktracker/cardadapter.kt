package com.trackerl.tasktracker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class cardadapter(song1:List<songs>): RecyclerView.Adapter<viewholder>() {
    val songparam=song1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val inflater:LayoutInflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.cardviewdesign,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return songparam.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.checkbox.setBackgroundColor(R.color.purple_500)
        holder.textview.text=songparam[position].datatask
    }
}



class viewholder(itemview: View):RecyclerView.ViewHolder(itemview){
   val checkbox=itemview.findViewById<CheckBox>(R.id.checkbox1)
 val textview=itemview.findViewById<TextView>(R.id.textView5)
}