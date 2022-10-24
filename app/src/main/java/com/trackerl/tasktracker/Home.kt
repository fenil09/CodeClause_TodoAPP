package com.trackerl.tasktracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Home : AppCompatActivity(){


    lateinit var mauth:FirebaseAuth
    lateinit var reff:DatabaseReference
    lateinit var task:ArrayList<datatasks>
     lateinit var view:RecyclerView
     var context:Context=this
    var itemposition:Int=0
    var sharedname:String="mypref"
    lateinit var reff2:DatabaseReference
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
        val adapter=cardadapter(task)
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


}