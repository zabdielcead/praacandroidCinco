package com.cead.androidcinco

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // diseño con android
        // funcionalidad de widget button checkbox etc....

    var btnX: Button ? = null
    val GREETER: String = "HELLOS FROM THE OTHER SIDE!"





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Action bar forzar y cargar icono en el Action Bar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setIcon(R.mipmap.ic_bar)


        btnX = findViewById(R.id.button)
        //click para la funcion de onclick
        btnX!!.setOnClickListener(this)

        //tipo de click
       /* btnX!!.setOnClickListener {
            Toast.makeText(this,"boton! XD click", Toast.LENGTH_LONG).show()
        }*/
    }


    // click en el layout
    fun pruebaBtns(view: View){ //onclick del xml
        Toast.makeText(this,"boton! XD ${view.id}", Toast.LENGTH_LONG).show()
    }


    //click para la funcion de onclick
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.button -> {
                Toast.makeText(this,"boton! XD onclick", Toast.LENGTH_LONG).show()
                var intent: Intent = Intent(this, SecondActivity::class.java) // intent explicito
                intent.putExtra("greeter", GREETER)
                startActivity(intent)
            }
        }
    }
}
