package com.trackerl.tasktracker

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.ncorti.slidetoact.SlideToActView
import java.util.Timer
import kotlin.concurrent.schedule

@Suppress("UNUSED_CHANGED_VALUE")
class createTask : AppCompatActivity(),SlideToActView.OnSlideCompleteListener{
    lateinit var data:EditText
    var userid:String=""
    lateinit var high:CheckBox
    var checkpriority:String=""
    lateinit var low:CheckBox
    lateinit var reff:DatabaseReference
    lateinit var dataobject:datatasks
    lateinit var swipe:SlideToActView
    lateinit var mauth:FirebaseAuth
    lateinit var  player:MediaPlayer
    lateinit var timer:Timer
    lateinit var seetask:Intent
    lateinit var datalist:ArrayList<String>
    lateinit var preff:SharedPreferences
    val sharedname:String="mypreff"
    val key:String="counterkey"
    var length:Int=0
    var counter=0
  var id:String=""
    var counterdata:Int=0
    lateinit var editor:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
         data=findViewById(R.id.edit1)
         swipe=findViewById(R.id.swipe1)
        high=findViewById(R.id.checkBox2)
        low=findViewById(R.id.checkBox3)
        controlcheckbox()
        mauth= FirebaseAuth.getInstance()
        player= MediaPlayer()
        timer= Timer()
        seetask=Intent(this,Home::class.java)
       swipe.onSlideCompleteListener=this
       datalist=java.util.ArrayList()
        preff=getSharedPreferences(sharedname, MODE_PRIVATE)
        editor=preff.edit()
       swipe.setOnClickListener {
           checkdata()
       }



    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options,menu)
        //to create the menu opions in our activity we used the menu inflater
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.progress){
            val intent:Intent=Intent(this,Progress::class.java)
            startActivity(intent)

        }
        else{

            if(item.itemId==R.id.viewtasks){
                val intent:Intent=Intent(this,Home::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }




    override fun onSlideComplete(view: SlideToActView){

        sendatatofirebase()

    }


    fun controlcheckbox() {

        high.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            checkpriority="High Priority"
        low.isChecked=false
        })

        low.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->

            if(low.isChecked){
                high.isChecked=false
                low.isChecked=true
                checkpriority="Low Priority"
            }
        })
    }

    fun checkdata(){
        val value=data.text.toString()
        dataobject= datatasks(value,checkpriority)

        if(dataobject.datauser.isEmpty() || dataobject.priority.isEmpty()){
           val alert:AlertDialog.Builder=AlertDialog.Builder(this)
            alert.setMessage("please enter your task and also remember to select the priority of your task")
            alert.setPositiveButton("okay", DialogInterface.OnClickListener { dialog, which ->

                dialog.dismiss()

            })
            alert.setCancelable(false)
            alert.show()

        }

    }


    fun sendatatofirebase(){

        val value=data.text.toString()
        dataobject= datatasks(value,checkpriority)
        reff=FirebaseDatabase.getInstance().reference
        reff.child("usertasks").child(mauth.uid.toString()).push()
            .setValue(dataobject).addOnCompleteListener(this){

            if(it.isSuccessful){
                data.setText("")
                high.isChecked=false
                low.isChecked=false
                player=MediaPlayer.create(this,R.raw.gpay)
                player.start()
                handlecounterupdate()
                timer.schedule(2000){
                    startActivity(seetask)
                }
                Toast.makeText(this,"task added successfully",Toast.LENGTH_LONG).show()


            }
            else{
                Toast.makeText(this,"sorry some server error occured",Toast.LENGTH_LONG).show()
            }
        }

    }


    fun handlecounterupdate(){

        var data=preff.getInt(key,0)
        if(data.equals(0)){
            counter=1
            editor.putInt(key,counter).commit()
        }
        else{
            data++
            editor.putInt(key,data).commit()
            reff.child("datacounter").child(mauth.uid.toString()).setValue(data)
        }

    }




}