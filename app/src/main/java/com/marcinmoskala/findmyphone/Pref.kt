package com.marcinmoskala.findmyphone

import com.marcinmoskala.kotlinpreferences.PreferenceHolder

object Pref: PreferenceHolder() {

    var token: String? by bindToPreferenceFieldNullable()
    var uid: String? by bindToPreferenceFieldNullable()
}