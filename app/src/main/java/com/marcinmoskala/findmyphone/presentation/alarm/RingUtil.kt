package com.marcinmoskala.findmyphone.presentation.alarm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager.*
import android.net.Uri
import com.marcinmoskala.findmyphone.Pref
import java.io.IOException

object RingUtil {

    private val RESULT_PICK_RING = 0
    private var mRingUri: Uri? = null

    fun pickRing(activity: Activity) {
        val intent = Intent(ACTION_RINGTONE_PICKER).apply {
            putExtra(EXTRA_RINGTONE_TYPE, TYPE_NOTIFICATION)
            putExtra(EXTRA_RINGTONE_TITLE, "Select Tone")
            putExtra(EXTRA_RINGTONE_EXISTING_URI, if (mRingUri != null) mRingUri else null)
        }
        activity.startActivityForResult(intent, RESULT_PICK_RING)
    }

    fun setRingUri(context: Context, ringUri: Uri) {
        Pref.ringToneUri = ringUri.toString()
    }

    fun playRing(context: Context, mediaPlayer: MediaPlayer) {
        mRingUri = Uri.parse(Pref.ringToneUri)

        try {
            mediaPlayer.setDataSource(context, mRingUri!!)
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM)
                mediaPlayer.isLooping = true
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun stopRing(mediaPlayer: MediaPlayer) {
        mediaPlayer.stop()
    }
}