package com.marcinmoskala.findmyphone.presentation.alarm

import activitystarter.MakeActivityStarter
import android.app.Activity
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import com.marcinmoskala.findmyphone.Pref
import com.marcinmoskala.findmyphone.R
import com.marcinmoskala.findmyphone.utills.audioManager
import com.marcinmoskala.kotlinandroidviewbindings.bindToClick

@MakeActivityStarter
class AlarmActivity : Activity() {

    private val mediaPlayer by lazy { MediaPlayer() }

    var dismissButtonClicked by bindToClick(R.id.dismissButton)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        window.addFlags(FLAG_KEEP_SCREEN_ON or FLAG_DISMISS_KEYGUARD or FLAG_SHOW_WHEN_LOCKED or FLAG_TURN_SCREEN_ON)
        playRing()
        dismissButtonClicked = {
            stopRing()
            finish()
        }
    }

    fun playRing() {
        val ringUri = Uri.parse(Pref.ringToneUri)

        try {
            mediaPlayer.apply {
                setDataSource(this@AlarmActivity, ringUri)
                if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                    setAudioStreamType(AudioManager.STREAM_ALARM)
                    isLooping = true
                    prepare()
                    start()
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    fun stopRing() {
        mediaPlayer.stop()
    }

    override fun onBackPressed() {
        //Can't back out
    }
}