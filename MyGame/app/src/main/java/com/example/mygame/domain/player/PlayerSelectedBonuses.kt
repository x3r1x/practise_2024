package com.example.mygame.domain.player

import android.graphics.Paint
import android.os.CountDownTimer
import com.example.mygame.UI.GameSoundsPlayer
import com.example.mygame.domain.GameConstants

class PlayerSelectedBonuses(private val player: Player) {
    private val shieldPaint = Paint()
    private val jetpackPaint = Paint()

    private var timeWithShield = 0f
    private var shieldPulseTime = 0f

    private var timeWithJetpack = 0f
    private var jetpackPulseTime = 0f
    private var jetpackSound = 0

    fun selectShield(audioPlayer: GameSoundsPlayer) {
        player.isWithShield = true
        shieldPaint.alpha = GameConstants.SHIELD_DEFAULT_TRANSPARENCY

        audioPlayer.playShieldPickupSound()
    }

    fun selectJetpack(audioPlayer: GameSoundsPlayer) {
        player.isWithJetpack = true
        jetpackPaint.alpha = GameConstants.JETPACK_DEFAULT_TRANSPARENCY

        jetpackSound = audioPlayer.playJetpackSound()
    }

    fun updateBonuses(additionalTime: Float, audioPlayer: GameSoundsPlayer) {
        if (player.isWithShield) {
            updateShield(additionalTime, audioPlayer)
        }
        if (player.isWithJetpack) {
            updateJetpack(additionalTime, audioPlayer)
        }
    }

    private fun updateJetpack(additionalTime: Float, audioPlayer: GameSoundsPlayer) {
        timeWithJetpack += additionalTime

        if (GameConstants.JETPACK_DURATION - timeWithJetpack <= 0f) {
            player.isWithJetpack = false

            audioPlayer.stopPlayingSound(jetpackSound)
            audioPlayer.playJetpackDestroySound()

            timeWithJetpack = 0f
            jetpackPulseTime = 0f
        } else if (GameConstants.JETPACK_DURATION - timeWithJetpack <= GameConstants.WHEN_JETPACK_TO_PULSE) {
            if (jetpackPulseTime == 0f) {
                audioPlayer.playBonusEndingSoonSound()
                jetpackPaint.alpha = GameConstants.JETPACK_PULSE_TRANSPARENCY
            }
            if (jetpackPulseTime >= GameConstants.JETPACK_TIMER_TICK) {
                jetpackPaint.alpha = GameConstants.JETPACK_DEFAULT_TRANSPARENCY
            }

            jetpackPulseTime += additionalTime

            if (jetpackPulseTime >= GameConstants.JETPACK_TIMER_TICK * 2) {
                jetpackPulseTime = 0f
            }
        }
    }

    private fun updateShield(additionalTime: Float, audioPlayer: GameSoundsPlayer) {
        timeWithShield += additionalTime

        println("#Debug: #Real: $timeWithShield")

        if (GameConstants.SHIELD_DURATION - timeWithShield <= 0f) {
            audioPlayer.playShieldDestroySound()

            player.isWithShield = false
            shieldPaint.alpha = 0

            timeWithShield = 0f
            shieldPulseTime = 0f
        } else if (GameConstants.SHIELD_DURATION - timeWithShield <= GameConstants.WHEN_SHIELD_TO_PULSE) {
            if (shieldPulseTime == 0f) {
                audioPlayer.playBonusEndingSoonSound()
                shieldPaint.alpha = GameConstants.SHIELD_PULSE_TRANSPARENCY
            }
            if (shieldPulseTime >= GameConstants.SHIELD_TIMER_TICK) {
                shieldPaint.alpha = GameConstants.SHIELD_DEFAULT_TRANSPARENCY
            }

            shieldPulseTime += additionalTime

            if (shieldPulseTime >= GameConstants.SHIELD_TIMER_TICK * 2) {
                shieldPulseTime = 0f
            }
        }
    }
}
