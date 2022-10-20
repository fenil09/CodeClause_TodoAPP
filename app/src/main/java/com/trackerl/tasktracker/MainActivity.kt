package com.trackerl.tasktracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var mauth:FirebaseAuth
    var uid:String=""
    lateinit var reff:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val go: Button =findViewById(R.id.button)
        mauth= FirebaseAuth.getInstance()
        Thread(Runnable {
            authenticationwithfirebase()

        }).start()


        go.setOnClickListener {
            gotohome()
        }


    }

    fun gotohome(){
        val intent:Intent=Intent(this,createTask::class.java)
        intent.putExtra("userid",uid)
        startActivity(intent)

        overridePendingTransition(R.anim.enteractivity,R.anim.enteractivity)
    }


    fun authenticationwithfirebase(){

        mauth.signInAnonymously().addOnCompleteListener(this){

            if(it.isSuccessful){
                uid=mauth.currentUser?.uid.toString()
                sendcountervaluetofirebase()
                sendcompletecountervaluetofirebase()
            }
        }
    }

    fun sendcountervaluetofirebase(){
        reff=FirebaseDatabase.getInstance().reference.child("datacounter").child(mauth.uid.toString())
         val data:Int=0
        reff.setValue(data)
    }


    fun sendcompletecountervaluetofirebase(){

        reff=FirebaseDatabase.getInstance().reference.child("completetaskcounter").child(mauth.uid.toString())
        val data:Int=0
        reff.setValue(data)


    }
}