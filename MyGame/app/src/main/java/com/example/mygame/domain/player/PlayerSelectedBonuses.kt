package com.example.mygame.domain.player

import android.graphics.Paint
import android.os.CountDownTimer
import com.example.mygame.UI.GameSoundsPlayer
import com.example.mygame.domain.GameConstants

class PlayerSelectedBonuses(private val player: Player) {
    var shieldPaint = Paint()
    var jetpackPaint = Paint()

    fun selectShield(audioPlayer: GameSoundsPlayer) {
        player.isWithShield = true
        shieldPaint.alpha = GameConstants.SHIELD_DEFAULT_TRANSPARENCY

        audioPlayer.playShieldPickupSound()

        val timer = object : CountDownTimer(GameConstants.SHIELD_DURATION, GameConstants.SHIELD_TIMER_TICK) {
            override fun onTick(p0: Long) {
                if (p0 <= GameConstants.WHEN_SHIELD_TO_PULSE) {
                    shieldPaint.alpha =
                        if (shieldPaint.alpha == GameConstants.SHIELD_PULSE_TRANSPARENCY) {
                            GameConstants.SHIELD_DEFAULT_TRANSPARENCY
                        } else {
                            audioPlayer.playBonusEndingSoonSound()

                            GameConstants.SHIELD_PULSE_TRANSPARENCY
                        }
                }
            }

            override fun onFinish() {
                audioPlayer.playShieldDestroySound()

                player.isWithShield = false
                shieldPaint.alpha = 0
            }
        }
        timer.start()
    }

    fun selectJetpack(audioPlayer: GameSoundsPlayer) {
        player.isWithJetpack = true
        jetpackPaint.alpha = GameConstants.JETPACK_DEFAULT_TRANSPARENCY

        val jetpackSound = audioPlayer.playJetpackSound()

        val timer = object : CountDownTimer(GameConstants.JETPACK_DURATION, GameConstants.JETPACK_TIMER_TICK) {
            override fun onTick(p0: Long) {
                if (p0 <= GameConstants.WHEN_JETPACK_TO_PULSE) {
                    jetpackPaint.alpha =
                        if (jetpackPaint.alpha == GameConstants.JETPACK_PULSE_TRANSPARENCY) {
                            GameConstants.JETPACK_DEFAULT_TRANSPARENCY
                        } else {
                            audioPlayer.playBonusEndingSoonSound()

                            GameConstants.JETPACK_PULSE_TRANSPARENCY
                        }
                }
            }

            override fun onFinish() {
                audioPlayer.playJetpackDestroySound()
                audioPlayer.stopPlayingSound(jetpackSound)

                player.isWithJetpack = false
            }
        }
        timer.start()
    }
}
