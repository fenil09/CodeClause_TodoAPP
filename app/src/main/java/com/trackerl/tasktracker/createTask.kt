package com.trackerl.tasktracker

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ncorti.slidetoact.SlideToActView

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
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
         data=findViewById(R.id.edit1)
         swipe=findViewById(R.id.swipe1)
        high=findViewById(R.id.checkBox2)
        low=findViewById(R.id.checkBox3)
        controlcheckbox()
        mauth= FirebaseAuth.getInstance()
       swipe.onSlideCompleteListener=this

       swipe.setOnClickListener {
           checkdata()
       }

    }


    override fun onSlideComplete(view: SlideToActView){

        sendatatofirebase()

    }


    fun controlcheckbox() {

        high.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            checkpriority="Yes"
        low.isChecked=false
        })

        low.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->

            if(low.isChecked){
                high.isChecked=false
                low.isChecked=true
                checkpriority="No"
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

        reff.child("usertasks").child(mauth.uid.toString()).push().setValue(dataobject).addOnCompleteListener(this){

            if(it.isSuccessful){
                data.setText("")
                high.isChecked=false
                low.isChecked=false
                Toast.makeText(this,"task added successfully",Toast.LENGTH_LONG).show()
                val intent:Intent=Intent(this,Home::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"sorry some server error occured",Toast.LENGTH_LONG).show()
            }
        }

    }



}