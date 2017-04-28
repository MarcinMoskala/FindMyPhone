package com.marcinmoskala.findmyphone.presentation.message

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.marcinmoskala.findmyphone.presentation.alarm.AlarmActivityStarter


class CustomFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        AlarmActivityStarter.start(this)
    }
}