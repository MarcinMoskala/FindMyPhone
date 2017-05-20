package com.marcinmoskala.findmyphone.presentation.message

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.marcinmoskala.findmyphone.presentation.alarm.AlarmActivityStarter


class CustomFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        AlarmActivityStarter.startWithFlags(this, Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}