package com.marcinmoskala.findmyphone.presentation.refreshtoken

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.marcinmoskala.findmyphone.saveToken

class RefreshTokenService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token ?: return
        saveToken(refreshedToken)
    }
}
