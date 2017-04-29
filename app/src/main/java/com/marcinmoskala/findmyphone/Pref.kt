package com.marcinmoskala.findmyphone

import android.media.RingtoneManager
import android.media.RingtoneManager.getDefaultUri
import com.marcinmoskala.kotlinpreferences.PreferenceHolder

object Pref : PreferenceHolder() {

    var token: String? by bindToPreferenceFieldNullable()
    var uid: String? by bindToPreferenceFieldNullable()
    var ringToneUri: String by bindToPreferenceField(getDefaultUri(RingtoneManager.TYPE_ALARM).toString())
}