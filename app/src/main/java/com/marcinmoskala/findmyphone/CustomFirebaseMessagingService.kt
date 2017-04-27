package com.marcinmoskala.findmyphone

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CustomFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: " + remoteMessage?.from);

        if (remoteMessage?.data?.isNotEmpty() ?: false) {
            Log.d(TAG, "Message data payload: " + remoteMessage?.data)
        }

        if (remoteMessage?.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification.body)
        }
    }
}