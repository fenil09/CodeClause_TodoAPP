package com.trackerl.tasktracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val go: Button =findViewById(R.id.button)

        go.setOnClickListener {
            gotohome()
        }


    }

    fun gotohome(){
        val intent:Intent=Intent(this,createTask::class.java)
        startActivity(intent)

        overridePendingTransition(R.anim.enteractivity,R.anim.enteractivity)
    }
}