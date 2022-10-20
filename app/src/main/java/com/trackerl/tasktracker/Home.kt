package com.trackerl.tasktracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import value
import kotlin.properties.Delegates

class Home : AppCompatActivity() ,cardadapter.onitemclickedlistner{


    lateinit var mauth:FirebaseAuth
    lateinit var reff:DatabaseReference
    lateinit var task:ArrayList<datatasks>
     lateinit var view:RecyclerView
     var context:Context=this
    var itemposition:Int=0
    var sharedname:String="mypref"
    var key:String="counterkey"
    lateinit var preff:SharedPreferences
    var name:String="fenil"

    lateinit var pref:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mauth=FirebaseAuth.getInstance()
        supportActionBar?.hide()
         view=findViewById(R.id.recycler1)
        task= arrayListOf<datatasks>()
        preff=getSharedPreferences(sharedname, MODE_PRIVATE)
        getuserdata()

    }




    override fun onBackPressed() {
        val intent: Intent =Intent(this,createTask::class.java)
        startActivity(intent)
    }

    fun getuserdata(){
        val adapter=cardadapter(task,this)
        reff=FirebaseDatabase.getInstance().getReference("usertasks").child(mauth.uid.toString())
       reff.addValueEventListener(object:ValueEventListener{
           @SuppressLint("NotifyDataSetChanged")
           override fun onDataChange(snapshot: DataSnapshot) {

               if(snapshot.exists()){
                   for(usersnapshot in snapshot.children){

                       val userdata=usersnapshot.getValue(datatasks::class.java)
                       if (userdata != null) {
                           task.add(userdata)
                       }

                   }
                   view.adapter=adapter
                   adapter.notifyDataSetChanged()
                   view.layoutManager=LinearLayoutManager(context)
               }


           }

           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }

       })

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onitemclicked(postion: Int) {

        pref=getSharedPreferences(sharedname, MODE_PRIVATE)

        Toast.makeText(this,"$postion is selectec",Toast.LENGTH_LONG).show()



    }






}