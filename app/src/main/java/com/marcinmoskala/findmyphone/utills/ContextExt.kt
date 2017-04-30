package com.marcinmoskala.findmyphone.utills

import android.content.Context

val Context.audioManager
    get() = getSystemService(Context.AUDIO_SERVICE) as android.media.AudioManager