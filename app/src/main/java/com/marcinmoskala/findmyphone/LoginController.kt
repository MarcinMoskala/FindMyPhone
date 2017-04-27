package com.marcinmoskala.findmyphone

import android.content.Intent

interface LoginController {
    fun onCreate()
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun onLoginClicked()
}