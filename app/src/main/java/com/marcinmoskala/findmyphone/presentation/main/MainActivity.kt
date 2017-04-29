package com.marcinmoskala.findmyphone.presentation.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.marcinmoskala.findmyphone.R
import com.marcinmoskala.findmyphone.utills.isVisible
import com.marcinmoskala.findmyphone.utills.loadImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    val controller: LoginController by lazy {
        GoogleLoginController(this, {
            loggedInPanelVisible = true
            iconView.loadImage(it.photoUrl)
            nameView.text = it.displayName
        }, {
            Toast.makeText(this, "Login error", Toast.LENGTH_LONG).show()
        })
    }

    var loggedInPanelVisible: Boolean by Delegates.observable(false) { _, _, n ->
        loggedPanel.isVisible = n
        loggedOutPanel.isVisible = !n
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller.onCreate()
        logInButton.setOnClickListener {
            controller.login()
        }
        logOutButton.setOnClickListener {
            controller.logOut {
                loggedInPanelVisible = false
            }
        }
        testServiceButton.setOnClickListener {

        }
    }

    override fun onStart() {
        super.onStart()
        controller.trySilentSignedIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        controller.onActivityResult(requestCode, resultCode, data)
    }
}
