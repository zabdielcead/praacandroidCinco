package com.cead.androidcinco

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class ThirdActivity : AppCompatActivity() {


     var editTexPhone: EditText? = null
     var editTexWeb: EditText? = null
     var imgBtnPhone:  ImageButton? = null;
     var imgBtnWeb:  ImageButton? = null;
     var imgBtnCamera:  ImageButton? = null;

     val PHONE_CALL_CODE: Int = 100
     val PICTURE_FROM_CAMERA: Int = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)


        //Activar flecha ir atras
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        editTexPhone = findViewById(R.id.edTel)
        editTexWeb = findViewById(R.id.edTxtWeb)
        imgBtnPhone = findViewById(R.id.imgBtnCall)
        imgBtnWeb = findViewById(R.id.imgWeb)
        imgBtnCamera = findViewById(R.id.imageButton3)



        //boton para llamada
        imgBtnPhone!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var phoneNumber = editTexPhone!!.text.toString()
                if(!phoneNumber.isEmpty()){
                    //comprobar version actual de android que estamos corriendo
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){


                        // comprobar si ha aceptado, no ha aceptado, o nunca se le ha preguntado
                        if(checkPermission(Manifest.permission.CALL_PHONE)){
                            // ha aceptado

                            var intent: Intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                            if(ActivityCompat.checkSelfPermission(this@ThirdActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){ // si lo habia denegado regresa n o hace la llamada
                                return
                            }
                            startActivity(intent)


                        }else{
                            // o no ha aceptado, o es la primera vez que se le pregunta

                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                // no se le ha preguntado aún
                                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), PHONE_CALL_CODE) //esta ligado al metodo onRequestPermissionsResult
                            }else {

                                // ha denegado el permiso
                                Toast.makeText(this@ThirdActivity, "enable the request permission", Toast.LENGTH_SHORT).show()
                                var intent : Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intent.addCategory(Intent.CATEGORY_DEFAULT)
                                intent.setData(Uri.parse("package:$packageName"))
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                                startActivity(intent)

                            }

                        }



                    }else{
                        olderVersions(phoneNumber)
                    }


                }else {
                    Toast.makeText(this@ThirdActivity, "ingresa un numero valido", Toast.LENGTH_SHORT).show()
                }

            }

            fun olderVersions(phoneNumber: String){
                var intentCall  = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"));
                if(checkPermission(Manifest.permission.CALL_PHONE)){
                    startActivity(intentCall) // despues de android 6 tienes que pedir permisos
                }else{
                    Toast.makeText(this@ThirdActivity, "you decline access", Toast.LENGTH_SHORT).show()
                }

            }



        })




        // boton para el navegador web
        imgBtnWeb!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var urlWeb = editTexWeb!!.text.toString()
                var email = "zabdiel.cead@gmail.com"

                if(urlWeb != null && !urlWeb.isEmpty()){

                    //intento web
                    var intentWeb =  Intent()
                    //var intentWeb =  Intent(Intent.ACTION_VIEW, Uri.parse("http://$urlWeb"))
                    intentWeb.setAction(Intent.ACTION_VIEW)
                    intentWeb.setData(Uri.parse("http://$urlWeb"))


                    //Intento de contactos
                    var intentContacts = Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"))


                    //email rapido
                    var intentMailTo = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email))


                    //email  completo
                    var intentMailCompleto = Intent(Intent.ACTION_SEND, Uri.parse(email))
                    //intentMailCompleto.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail")
                    intentMailCompleto.setType("plain/text")
                    intentMailCompleto.putExtra(Intent.EXTRA_SUBJECT,"MAIL TITLE")
                    intentMailCompleto.putExtra(Intent.EXTRA_TEXT,"Hi there, I love my form app")
                    intentMailCompleto.putExtra(Intent.EXTRA_EMAIL, arrayOf("fernando@gmail.com" , "anotonio@gmail.com"))
                    startActivity(Intent.createChooser(intentMailCompleto,"Elige cliente de corrreo")) // si sirve!!!!


                    // telefono 2 sin permisos requeridos
                    //var intentPhone = Intent(Intent.ACTION_CALL, Uri.parse("tel:66666145258"))





                    //startActivity(intentPhone)
                }
            }
        })



        // BOTON CAMARA
        imgBtnCamera!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // abrir  camara
                var intentCamara = Intent("android.media.action.IMAGE_CAPTURE")
                startActivityForResult(intentCamara, PICTURE_FROM_CAMERA) // se va a onActivityResult
            }
        })


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode){
                PICTURE_FROM_CAMERA -> {
                    if(resultCode == Activity.RESULT_OK){
                        var result = data!!.toUri(0)
                        Toast.makeText(this@ThirdActivity,"Result: " +result, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@ThirdActivity,"There was an error with the picture, try again", Toast.LENGTH_LONG).show()
                    }


                } else -> {

                }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }







    fun checkPermission (permission: String): Boolean { // comprobar permiso en el manifest versiones antiguas si acepto en el proceso de instalacion
        var result : Int = this.checkCallingOrSelfPermission(permission)

        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {   //grantresult es el permiso que dio el usuario si y no


        // ESTAMOS EN EL CASO DEL TELEFONO
       when (requestCode){
           PHONE_CALL_CODE -> {
                var permission: String = permissions[0]
                var result: Int =  grantResults[0]

                if(permission.equals(Manifest.permission.CALL_PHONE)){
                        // comprobar si ha sido aceptado o denegado la peticion del permiso
                    if( result == PackageManager.PERMISSION_GRANTED ){
                        // concedio permiso
                        var phoneNumber: String = editTexPhone!!.text.toString()
                        var intentCall: Intent =    Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber))
                        if(ActivityCompat.checkSelfPermission(this@ThirdActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){ // si lo habia denegado regresa n o hace la llamada
                            return
                        }
                        startActivity(intentCall)
                    }else {
                        // No concedio permiso
                        Toast.makeText(this@ThirdActivity, "you decline access", Toast.LENGTH_SHORT).show()
                    }
                }

           }
           else -> {
               super.onRequestPermissionsResult(requestCode, permissions, grantResults)
           }
       }


    }

}
