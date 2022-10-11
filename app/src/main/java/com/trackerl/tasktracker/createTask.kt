package com.trackerl.tasktracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.Toast
import com.ncorti.slidetoact.SlideToActView

class createTask : AppCompatActivity(),SlideToActView.OnSlideCompleteListener{
    lateinit var data:EditText
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
         data=findViewById(R.id.edit1)
        val swipe:SlideToActView=findViewById(R.id.swipe1)
       swipe.onSlideCompleteListener=this

    }


    override fun onSlideComplete(view: SlideToActView){
        display()

    }


    fun display(){
        Toast.makeText(this,"swipe completed",Toast.LENGTH_LONG).show()
    }




}