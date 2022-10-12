package com.trackerl.tasktracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        val task:MutableList<songs> = mutableListOf<songs>()
        task.add(songs("completed todolist app"))
        val view:RecyclerView=findViewById(R.id.recycler1)
        view.adapter=cardadapter(task)
        view.layoutManager=LinearLayoutManager(this)



    }
}