package com.marcinmoskala.findmyphone.presentation.main

import android.content.Intent

interface LoginController {
    fun onCreate()
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun login()
    fun trySilentSignedIn()
    fun logOut(callback: () -> Unit)
}