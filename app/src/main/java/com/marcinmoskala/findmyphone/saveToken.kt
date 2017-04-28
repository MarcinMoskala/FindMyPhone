package com.marcinmoskala.findmyphone

import com.google.firebase.database.FirebaseDatabase

fun saveToken(token: String) {
    Pref.token = token
    val uid = Pref.uid ?: return
    send(token, uid)
}

fun saveUid(uid: String?) {
    uid ?: return
    Pref.uid = uid
    val token = Pref.token ?: return
    send(token, uid)
}

private fun send(token: String, uid: String) {
    FirebaseDatabase
            .getInstance()
            .getReference(uid)
            .setValue(token)
}
