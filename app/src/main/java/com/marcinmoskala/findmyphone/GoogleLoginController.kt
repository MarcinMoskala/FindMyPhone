package com.marcinmoskala.findmyphone

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class GoogleLoginController(val activity: Activity, val onLogged: () -> Unit, val onError: (Throwable) -> Unit) : LoginController {

    private val RC_SIGN_IN = 9001

    val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val googleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .build()
    }
    val mGoogleApiClient: GoogleApiClient by lazy {
        GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addOnConnectionFailedListener { onError(Error(it.errorMessage)) }
                .build()
    }

    override fun onCreate() {
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            saveUid(auth.currentUser?.uid)
        }
    }

    override fun onLoginClicked() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount ?: return
                firebaseAuthWithGoogle(account)
            } else {
                onError(Error("Google login error: " + result.status))
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                onLogged()
                val user = mAuth.currentUser ?: return@addOnCompleteListener
                val uid = user.uid
                saveUid(uid)
            } else {
                Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}