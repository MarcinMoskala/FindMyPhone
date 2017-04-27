package com.marcinmoskala.findmyphone

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val controller: LoginController by lazy { GoogleLoginController(this, {
        Toast.makeText(this, "Correct :)", Toast.LENGTH_LONG).show()
    }, {
        Toast.makeText(this, "Error :(", Toast.LENGTH_LONG).show()
    }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller.onCreate()
    }

    override fun onStart() {
        super.onStart()
        controller.onLoginClicked()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        controller.onActivityResult(requestCode, resultCode, data)
    }
}
