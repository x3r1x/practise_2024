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
    private val enemySlainSound = player.load(context, R.raw.enemy_kill_sound, BASE_PRIORITY)
    private val playerKilledSound = player.load(context, R.raw.death_from_enemy_sound, BASE_PRIORITY)

    fun playShootSound() {
        player.play(shootSound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
    }

    fun playJumpSound() {
        player.play(jumpSound, JUMP_VOLUME, JUMP_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
    }

    fun playSpringSound() {
        player.play(springSound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
    }

    fun playEnemyShotSound() {
        player.play(enemyShotSound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
    }

    fun playShieldPickupSound() {
        player.play(shieldPickupSound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
    }

    fun playShieldDestroySound() {
        player.play(shieldDestroySound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
    }

    fun playJetpackSound() : Int {
        return player.play(jetpackSound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, LOOP, BASE_SPEED_RATE)
    }

    fun playJetpackDestroySound() {
        player.play(jetpackDestroySound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
    }

    fun playBonusEndingSoonSound() {
        player.play(bonusEndingSoonSound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
    }

    fun playEnemySlainSound() {
        player.play(enemySlainSound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
    }

    fun playPlayerKilledSound() {
        player.play(playerKilledSound, MAX_VOLUME, MAX_VOLUME, BASE_PRIORITY, NO_LOOP, BASE_SPEED_RATE)
    }

    fun stopPlayingSound(sound: Int) {
        player.stop(sound)
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