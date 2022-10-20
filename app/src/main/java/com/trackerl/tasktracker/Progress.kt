package com.trackerl.tasktracker

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Progress : AppCompatActivity() {
    lateinit var reff:DatabaseReference
    lateinit var mauth:FirebaseAuth
    lateinit var totaltask:TextView
    lateinit var completedtask:TextView
    lateinit var bar:ProgressBar
    lateinit var viewprogress:TextView
    val sharedname1:String="mypreff"
    val key:String="counterkey"
    lateinit var  newpreff:SharedPreferences
    lateinit var newpreff2:SharedPreferences
    val sharedname:String="mypreff3"
    val sharedname2:String="newmypreff"
    val key2:String="completetaskcounter"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        mauth = FirebaseAuth.getInstance()
        totaltask = findViewById(R.id.textView8)
        completedtask = findViewById(R.id.textView10)
        bar = findViewById(R.id.progressBar2)
        viewprogress = findViewById(R.id.textView7)
        newpreff = getSharedPreferences(sharedname1, MODE_PRIVATE)
        newpreff2=getSharedPreferences(sharedname2, MODE_PRIVATE)
        getdata()

    }

        fun getdata() {
            val total = newpreff.getInt(key, 0)
            totaltask.text = total.toString()
            val completed = newpreff2.getInt(key2, 0)
            completedtask.text = completed.toString()

            val totalprogress:Int=100
            val check:Int=totalprogress/total
            val setprogress:Int=completed*check
            bar.progress=setprogress
            viewprogress.text="$setprogress%"
            reff=FirebaseDatabase.getInstance().reference.child("progressvalue").child(mauth.uid.toString())
            reff.setValue(setprogress)
        }

    }
