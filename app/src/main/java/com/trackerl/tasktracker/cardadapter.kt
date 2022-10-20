package com.trackerl.tasktracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.trackerl.tasktracker.R.color.teal_700
import value

class cardadapter(data1:ArrayList<datatasks>,onitemclickedlistner1:onitemclickedlistner): RecyclerView.Adapter<viewholder>() {


   lateinit var  reff:DatabaseReference
    val dataparam=data1
    val clickedparamm=onitemclickedlistner1
   lateinit var  preff:SharedPreferences
   val shared:String="newmypreff"
    val key:String="completetaskcounter"
    var holdposion:Int=-1
    var counter:Int=0
    lateinit var editor: Editor
    lateinit var mauth:FirebaseAuth
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val inflater:LayoutInflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.cardviewdesign,parent,false)
        preff=view.context.getSharedPreferences(shared,Context.MODE_PRIVATE)
        editor=preff.edit()
        mauth= FirebaseAuth.getInstance()

        return viewholder(view,clickedparamm)
    }

    override fun getItemCount(): Int {
        return dataparam.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: viewholder, position: Int) {


        var statedchecked:Boolean
        holder.checkbox.setBackgroundColor(R.color.purple_500)
        holder.textview.text=dataparam[position].datauser

        holder.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            var data=preff.getInt(key,0)
            if(holder.checkbox.isChecked){
                if(data.equals(0)){
                    counter=1
                    editor.putInt(key,counter).commit()
                    reff=FirebaseDatabase.getInstance().reference.child("completetaskcounter").child(mauth.uid.toString())
                    reff.setValue(counter)
                }
                else{
                    data += 1
                    editor.putInt(key,data).commit()
                    reff=FirebaseDatabase.getInstance().reference.child("completetaskcounter").child(mauth.uid.toString())
                    reff.setValue(data)

                }


            }
            else{

                data -= 1
                editor.putInt(key,data).commit()
                reff=FirebaseDatabase.getInstance().reference.child("completetaskcounter").child(mauth.uid.toString())
                reff.setValue(data)

            }


        }
    }



    interface onitemclickedlistner{

        fun onitemclicked(postion:Int)
    }


}



@SuppressLint("ResourceAsColor")
class viewholder(itemview: View, onclicked:cardadapter.onitemclickedlistner):RecyclerView.ViewHolder(itemview){
    val sharename:String="mypref"
    val keybox:String="statechecked"
   val checkbox=itemview.findViewById<CheckBox>(R.id.checkBox5)
 val textview=itemview.findViewById<TextView>(R.id.textView5)

    }






