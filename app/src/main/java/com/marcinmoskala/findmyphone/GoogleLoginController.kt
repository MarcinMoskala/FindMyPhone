package com.marcinmoskala.findmyphone

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.marcinmoskala.findmyphone.LoginController


class GoogleLoginController(val activity: Activity, val onLogged: (String)->Unit, val onError: (Throwable)->Unit): LoginController {

    private val RC_SIGN_IN = 9001

    lateinit var mGoogleApiClient: GoogleApiClient

    override fun onCreate() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addOnConnectionFailedListener { onError(Error(it.errorMessage)) }
                .build()
    }

    override fun onLoginClicked() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val signInAccount = result.signInAccount
                val email = signInAccount?.email
                email?.let { onLogged(it) }
            } else {
                onError(Error("Google login error: " + result.status))
            }
        }
    }
}