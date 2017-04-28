package com.marcinmoskala.findmyphone

import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class FirebaseService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token ?: return
        saveToken(refreshedToken)
    }
}
