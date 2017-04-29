package com.marcinmoskala.findmyphone.presentation.alarm

import activitystarter.MakeActivityStarter
import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import com.marcinmoskala.findmyphone.R
import kotlinx.android.synthetic.main.activity_alarm.*

@MakeActivityStarter
class AlarmActivity : Activity() {

    private lateinit var mMediaPlayer: MediaPlayer

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        window.addFlags(FLAG_KEEP_SCREEN_ON or FLAG_DISMISS_KEYGUARD or FLAG_SHOW_WHEN_LOCKED or FLAG_TURN_SCREEN_ON)
        mMediaPlayer = MediaPlayer()
        RingUtil.playRing(this@AlarmActivity, mMediaPlayer)
        dismissButton.setOnClickListener {
            RingUtil.stopRing(mMediaPlayer)
            finish()
        }
    }

    override fun onBackPressed() {
        //Can't back out
    }
}