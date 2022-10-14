package com.trackerl.tasktracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class Home : AppCompatActivity() {


    lateinit var mauth:FirebaseAuth
    lateinit var reff:DatabaseReference
    lateinit var task:ArrayList<datatasks>
     lateinit var view:RecyclerView
     var context:Context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        mauth=FirebaseAuth.getInstance()
         view=findViewById(R.id.recycler1)
        task= arrayListOf<datatasks>()
        getuserdata()

    }


    override fun onBackPressed() {
        val intent: Intent =Intent(this,createTask::class.java)
        startActivity(intent)
    }

    fun getuserdata(){

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
                  val adapter=cardadapter(task)
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