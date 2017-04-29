package com.marcinmoskala.findmyphone.presentation.main

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.marcinmoskala.findmyphone.R
import com.marcinmoskala.findmyphone.saveUid
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.OptionalPendingResult




class GoogleLoginController(
        val activity: Activity,
        val onLogged: (GoogleSignInAccount) -> Unit,
        val onError: (Throwable) -> Unit
) : LoginController {

    private val RC_SIGN_IN = 9001

    val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    val googleSignInOptions by lazy {
        val clientId = activity.getString(R.string.default_web_client_id)
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .build()
    }
    val googleApiClient: GoogleApiClient by lazy {
        GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addOnConnectionFailedListener { onError(Error(it.errorMessage)) }
                .build()
    }

    override fun trySilentSignedIn() {
        val opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient)
        if (opr.isDone) {
            handleResult(opr.get())
        }
    }

    override fun onCreate() {
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            saveUid(auth.currentUser?.uid)
        }
    }

    override fun login() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun logOut(callback: () -> Unit) {
        googleApiClient.connect()
        googleApiClient.registerConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
            override fun onConnected(bundle: Bundle?) {
                FirebaseAuth.getInstance().signOut()
                if (googleApiClient.isConnected) {
                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { status ->
                        if (status.isSuccess) {
                            callback()
                        }
                    }
                }
            }

            override fun onConnectionSuspended(i: Int) {
                Log.d(TAG, "Google API Client Connection Suspended")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleResult(result)
        }
    }

    private fun handleResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount ?: return
            firebaseAuthWithGoogle(account)
        } else {
            onError(Error("Google login error: " + result.status))
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                val user = mAuth.currentUser ?: return@addOnCompleteListener
                val uid = user.uid
                onLogged(acct)
                saveUid(uid)
            } else {
                Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}