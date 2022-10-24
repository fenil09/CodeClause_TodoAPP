package com.trackerl.tasktracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class cardadapter(data1: ArrayList<datatasks>): RecyclerView.Adapter<viewholder>(){


   lateinit var  reff:DatabaseReference
    val dataparam=data1
   lateinit var  preff:SharedPreferences
   lateinit var preff2:SharedPreferences
   val shared:String="newmypreff"
    val key:String="completetaskcounter"
    val share2:String="savestate"
    val key2:String="checkedstate"
    lateinit var preff3:SharedPreferences
    lateinit var editor2:SharedPreferences.Editor
    lateinit var editor3:SharedPreferences.Editor
    val share3:String="mypreff3"
    val key3:String="tracktask"
    val key4:String="check"
    val key5:String="check2"
    var holdposion:Int=-1
    var counter:Int=0
    var counterkey:String="key"
    lateinit var editor: Editor
    lateinit var mauth:FirebaseAuth
    lateinit var reff2:DatabaseReference
    lateinit var refflist:ArrayList<Int>
    lateinit var player:MediaPlayer
    lateinit var context: Context
    var isselected:Boolean=false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val inflater:LayoutInflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.cardviewdesign,parent,false)
        preff=view.context.getSharedPreferences(shared,Context.MODE_PRIVATE)
        preff2=view.context.getSharedPreferences(share2,Context.MODE_PRIVATE)
        preff3=view.context.getSharedPreferences(share3,Context.MODE_PRIVATE)
        editor=preff.edit()
        editor2=preff2.edit()
        editor3=preff3.edit()
        mauth= FirebaseAuth.getInstance()
        refflist=java.util.ArrayList()
        player= MediaPlayer()
        context=view.context
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return dataparam.size
    }

    @SuppressLint("ResourceAsColor", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: viewholder, position: Int) {

        val index=position


        var statedchecked:Boolean
        holder.checkbox.setBackgroundColor(R.color.purple_500)
        holder.textview.text=dataparam[position].datauser


        holder.textview.setOnTouchListener(object:OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {


              when(event?.action){

                  MotionEvent.ACTION_DOWN -> {
                      val prioritydata=dataparam[index].priority
                      holder.textview.text=prioritydata
                  }
                  MotionEvent.ACTION_UP ->{
                      val taskdata=dataparam[index].datauser
                      holder.textview.text=taskdata
                  }
              }
               return true
            }

        })

        holder.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

            player= MediaPlayer.create(context,R.raw.applepay)
            player.start()
            var data=preff.getInt(key,0)
            if(holder.checkbox.isChecked){
                if(data.equals(0)){
                    counter=1
                    editor.putInt(key,counter).commit()
                    reff=FirebaseDatabase.getInstance().reference.child("completetaskcounter").child(mauth.uid.toString())
                    reff.setValue(counter)
                   reff2=FirebaseDatabase.getInstance().reference.child("usertasks").child(mauth.uid.toString())


                }
                else{
                    data += 1
                    editor.putInt(key,data).commit()
                    reff=FirebaseDatabase.getInstance().reference.child("completetaskcounter").child(mauth.uid.toString())
                    reff.setValue(data)
                }
                val index=position
                editor3.putInt(key4,index).commit()
                val counter:Int=1
                editor3.putInt(key5,counter).commit()
                refflist.add(index)

            }
            else{
                val state:Boolean=preff2.getBoolean(key2,false)
                holder.checkbox.isSelected=state
                data -= 1
                editor.putInt(key,data).commit()
                reff=FirebaseDatabase.getInstance().reference.child("completetaskcounter").child(mauth.uid.toString())
                reff.setValue(data)
            }


        }


        val checkdata1=preff3.getInt(key5,0)
        if(checkdata1.equals(1)){

            var checkdata2=preff3.getInt(key4,0)
            dataparam[checkdata2].datauser="task completed"
            holder.textview.text=dataparam[position].datauser
            checkdata2=0
            editor3.putInt(key4,checkdata2).commit()
        }
        else{
            holder.textview.text=dataparam[position].datauser
        }





    }



}



@SuppressLint("ResourceAsColor")
class viewholder(itemview: View):RecyclerView.ViewHolder(itemview){
    val sharename:String="mypref"
    val keybox:String="statechecked"
   val checkbox=itemview.findViewById<CheckBox>(R.id.checkBox5)
 val textview=itemview.findViewById<TextView>(R.id.textView5)
    }






