package com.example.mygame.UI

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.mygame.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameSoundsPlayer(context: Context, private val coroutineScope: LifecycleCoroutineScope) {
    private val attributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    private val player: SoundPool = SoundPool.Builder()
        .setAudioAttributes(attributes)
        .setMaxStreams(MAX_STREAMS)
        .build()

    private val shootSound = player.load(context, R.raw.shoot_sound, BASE_PRIORITY)
    private val jumpSound = player.load(context, R.raw.jump_sound, BASE_PRIORITY)
    private val springSound = player.load(context, R.raw.spring_sound, BASE_PRIORITY)
    private val enemyShotSound = player.load(context, R.raw.enemy_shooted, BASE_PRIORITY)
    private val shieldPickupSound = player.load(context, R.raw.shield_sound, BASE_PRIORITY)
    private val shieldDestroySound = player.load(context, R.raw.shield_destroy_sound, BASE_PRIORITY)
    private val jetpackSound = player.load(context, R.raw.jetpack_sound, BASE_PRIORITY)
    private val jetpackDestroySound = player.load(context, R.raw.jetpack_disappear_sound, BASE_PRIORITY)
    private val bonusEndingSoonSound = player.load(context, R.raw.bonus_is_ending_sound, BASE_PRIORITY)
    private val enemySlainWithBonusSound = player.load(context, R.raw.enemy_kill_with_bonus_sound, BASE_PRIORITY)
    private val enemySlainSound = player.load(context, R.raw.enemy_kill_sound, BASE_PRIORITY)
    private val playerKilledSound = player.load(context, R.raw.death_from_enemy_sound, BASE_PRIORITY)
    private val breakingPlatformSound = player.load(context, R.raw.breaking_platform_sound, BASE_PRIORITY)

    fun playShootSound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(shootSound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
        }
    }

    fun playJumpSound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                jumpSound,
                JUMP_VOLUME,
                JUMP_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
            )
        }
    }

    fun playSpringSound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                springSound,
                MAX_VOLUME,
                MAX_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
            )
        }
    }

    fun playEnemyShotSound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                enemyShotSound,
                MAX_VOLUME,
                MAX_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
            )
        }
    }

    fun playShieldPickupSound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                shieldPickupSound,
                MAX_VOLUME,
                MAX_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
            )
        }
    }

    fun playShieldDestroySound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                shieldDestroySound,
                MAX_VOLUME,
                MAX_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
            )
        }
    }

    fun playJetpackSound() : Int {
        return player.play(jetpackSound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, LOOP, BASE_SPEED_RATE)
    }

    fun playJetpackDestroySound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                jetpackDestroySound,
                MAX_VOLUME,
                MAX_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
            )
        }
    }

    fun playBonusEndingSoonSound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                bonusEndingSoonSound,
                MAX_VOLUME,
                MAX_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
            )
        }
    }

    fun playEnemySlainWithBonusSound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                enemySlainWithBonusSound,
                MAX_VOLUME,
                MAX_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
            )
        }
    }

    fun playEnemySlainSound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                enemySlainSound,
                MAX_VOLUME,
                MAX_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
            )
        }
    }

    fun playPlayerKilledSound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                playerKilledSound,
                MAX_VOLUME,
                MAX_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
            )
        }
    }

    fun playBreakingPlatformSound() {
        coroutineScope.launch(Dispatchers.IO) {
            player.play(
                breakingPlatformSound,
                MAX_VOLUME,
                MAX_VOLUME,
                BASE_PRIORITY,
                NO_LOOP,
                BASE_SPEED_RATE
                )
        }
    }

    fun stopPlayingSound(sound: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            player.stop(sound)
        }
    }

    fun release() {
        player.release()
    }

    companion object {
        const val BASE_PRIORITY = 0
        const val BASE_SPEED_RATE = 1f

        const val NO_LOOP = 0
        const val LOOP = -1

        const val MAX_VOLUME = 1f
        const val JUMP_VOLUME = 0.5f

        private const val MAX_STREAMS = 4
    }
}