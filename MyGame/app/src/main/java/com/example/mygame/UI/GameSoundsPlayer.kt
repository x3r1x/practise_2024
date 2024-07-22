package com.example.mygame.UI

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.mygame.R

class GameSoundsPlayer(context: Context) {
    private val attributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    val player: SoundPool = SoundPool.Builder()
        .setAudioAttributes(attributes)
        .setMaxStreams(MAX_STREAMS)
        .build()

    val shootSound = player.load(context, R.raw.shoot_sound, BASE_PRIORITY)
    val jumpSound = player.load(context, R.raw.jump_sound, BASE_PRIORITY)
    val springSound = player.load(context, R.raw.spring_sound, BASE_PRIORITY)
    val enemyShotSound = player.load(context, R.raw.enemy_shooted, BASE_PRIORITY)
    val shieldPickupSound = player.load(context, R.raw.shield_sound, BASE_PRIORITY)
    val shieldDestroySound = player.load(context, R.raw.shield_destroy_sound, BASE_PRIORITY)
    val jetpackSound = player.load(context, R.raw.jetpack_sound, BASE_PRIORITY)
    val jetpackDestroySound = player.load(context, R.raw.jetpack_disappear_sound, BASE_PRIORITY)
    val bonusEndingSoonSound = player.load(context, R.raw.bonus_is_ending_sound, BASE_PRIORITY)
    val enemySlainSound = player.load(context, R.raw.enemy_kill_sound, BASE_PRIORITY)
    val playerKilledSound = player.load(context, R.raw.death_from_enemy_sound, BASE_PRIORITY)

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