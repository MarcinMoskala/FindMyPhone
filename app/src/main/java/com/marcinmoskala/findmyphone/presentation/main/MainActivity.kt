package com.marcinmoskala.findmyphone.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import com.marcinmoskala.findmyphone.R
import com.marcinmoskala.findmyphone.presentation.alarm.AlarmActivityStarter
import com.marcinmoskala.findmyphone.utills.loadImage
import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.marcinmoskala.kotlinandroidviewbindings.bindToLoading
import com.marcinmoskala.kotlinandroidviewbindings.bindToTextView

class MainActivity : AppCompatActivity() {

    var displayName by bindToTextView(R.id.nameView)
    var loggedInPanelVisible: Boolean by bindToLoading(R.id.loggedPanel, R.id.loggedOutPanel)

    var logInButtonClicked by bindToClick(R.id.logInButton)
    var testButtonClicked by bindToClick(R.id.testButton)
    var logOutButtonClicked by bindToClick(R.id.logOutButton)
    var goToWebsideButtonClicked by bindToClick(R.id.goToWebsideButton)

    val controller: LoginController by lazy {
        GoogleLoginController(this, {
            loggedInPanelVisible = true
            // Find a way to make smart binding on KotlinAndroidViewBinding
            (findViewById(R.id.iconView) as ImageView).loadImage(it.photoUrl)
            displayName = it.displayName ?: ""
        }, {
            Toast.makeText(this, "Login error", Toast.LENGTH_LONG).show()
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller.onCreate()
        logInButtonClicked = { controller.login() }
        testButtonClicked = { AlarmActivityStarter.start(this) }
        logOutButtonClicked = { controller.logOut { loggedInPanelVisible = false } }
        goToWebsideButtonClicked = { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://findmyphone.fun/"))) }
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
