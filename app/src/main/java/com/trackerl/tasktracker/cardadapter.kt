package com.trackerl.tasktracker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class cardadapter(data1:ArrayList<datatasks>): RecyclerView.Adapter<viewholder>() {
    val dataparam=data1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val inflater:LayoutInflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.cardviewdesign,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return dataparam.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.checkbox.setBackgroundColor(R.color.purple_500)
        holder.textview1.text=dataparam[position].datauser
        holder.textview.text=dataparam[position].priority
    }
}



class viewholder(itemview: View):RecyclerView.ViewHolder(itemview){
   val checkbox=itemview.findViewById<CheckBox>(R.id.checkbox1)
 val textview=itemview.findViewById<TextView>(R.id.textView5)
  val textview1=itemview.findViewById<TextView>(R.id.textView7)
}