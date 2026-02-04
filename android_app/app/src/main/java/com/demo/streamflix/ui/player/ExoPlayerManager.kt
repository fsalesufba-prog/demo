package com.demo.streamflix.ui.player

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.util.MimeTypes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExoPlayerManager @Inject constructor() {

    fun createPlayer(context: Context): ExoPlayer {
        val trackSelector = DefaultTrackSelector(context).apply {
            setParameters(
                buildUponParameters()
                    .setMaxVideoSizeSd()
                    .setPreferredAudioLanguage("es")
                    .setSelectUndeterminedTextLanguage(true)
            )
        }

        return ExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .setHandleAudioBecomingNoisy(true)
            .setWakeMode(C.WAKE_MODE_NETWORK)
            .build()
            .apply {
                // Configurações do player
                repeatMode = Player.REPEAT_MODE_OFF
                playWhenReady = true
                setHandleAudioBecomingNoisy(true)
            }
    }

    fun releasePlayer(player: ExoPlayer?) {
        player?.release()
    }
}