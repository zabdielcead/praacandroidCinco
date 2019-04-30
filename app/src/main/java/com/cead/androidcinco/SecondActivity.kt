package com.cead.androidcinco

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {


    var txtViewmsj : TextView ? = null
    var btnActividadtres: Button ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        //Activar flecha ir atras
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        txtViewmsj = findViewById(R.id.textView)
        btnActividadtres = findViewById(R.id.btnActividadtres)

        var bundle:Bundle? = intent.extras

        if(bundle != null){

            Toast.makeText(this,   bundle.getString("greeter"), Toast.LENGTH_LONG).show()
            textView.text =  bundle.getString("greeter")
        }else{
            Toast.makeText(this,   "it is empty", Toast.LENGTH_LONG).show()
        }


        btnActividadtres!!.setOnClickListener{

            var intent = Intent(this@SecondActivity, ThirdActivity::class.java)
            startActivity(intent)
        }




    }
}
