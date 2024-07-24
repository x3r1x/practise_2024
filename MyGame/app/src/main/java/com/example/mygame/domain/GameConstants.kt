package com.example.mygame.domain

class GameConstants {
    companion object {
        const val GRAVITY = 2200f

        const val SENSOR_MULTIPLIER = 180f

        const val PLAYER_JUMP_SPEED = 1600f
        const val PLAYER_SPRING_JUMP_SPEED = 3700f
        const val PLAYER_SPEED_WITH_JETPACK = 1900f
        const val PLAYER_ON_X_MULTIPLIER = 1.5f
        const val PLAYER_DISTANCE_TO_TURN = 25f
        const val PLAYER_LEG_OFFSET_X_WHEN_SHOOT = 16f
        const val PLAYER_SMALL_LEG_OFFSET = 15f
        const val PLAYER_BIG_LEG_OFFSET = 50f

        const val PLATFORM_ON_X_SPEED = 200f
        const val PLATFORM_ON_Y_SPEED = 200f
        const val PLATFORM_ON_Y_RANGE = 700f

        const val STATIC_PLATFORM_COUNT_PER_LEVEL = 3

        const val SHIELD_DEFAULT_TRANSPARENCY = 128
        const val SHIELD_PULSE_TRANSPARENCY = 64
        const val JETPACK_DEFAULT_TRANSPARENCY = 255
        const val JETPACK_PULSE_TRANSPARENCY = 128

        const val JETPACK_DURATION = 6f // >= 3f
        const val SHIELD_DURATION = 10f // >= 4f

        const val SHIELD_TIMER_TICK = 0.5f
        const val JETPACK_TIMER_TICK = 0.5f

        const val WHEN_SHIELD_TO_PULSE = 3f
        const val WHEN_JETPACK_TO_PULSE = 2f

        const val BULLY_DEATH_OFFSET_X = 60f
        const val FLY_MOVE_SPEED = 520f
        const val NINJA_START_SPEED_X = 300f
        const val NINJA_MIN_SPEED_X = 130
        const val NINJA_MAX_SPEED_X = 650
        const val NINJA_MIN_CONVERGENCE = 10f
        const val NINJA_MAX_CONVERGENCE = 600f
        const val NINJA_SPEED_CHANGE_CHANCE = 0.05f

        const val PLATFORM_SPAWN_ADDITIONAL_X = 100f
        const val MAX_VERTICAL_PLATFORM_GAP = 300f
        const val MAX_VERTICAL_BREAKING_PLATFORM_GAP = 50f

        const val BONUSES_IN_INITIAL_PACK = 3
        const val ENEMIES_IN_INITIAL_PACK = 2

        const val INITIAL_JETPACK_SPAWN_CHANCE = 0.05f
        const val INITIAL_SHIELD_SPAWN_CHANCE = 0.2f

        const val INITIAL_NINJA_SPAWN_CHANCE = 0.05f
        const val INITIAL_FLY_SPAWN_CHANCE = 0.4f

        const val JETPACK_SPAWN_CHANCE = 0.02f
        const val SHIELD_SPAWN_CHANCE = 0.08f
        const val SPRING_SPAWN_CHANCE = 0.25f
        const val MIN_SPRING_SPAWN_X = 40
        const val MAX_SPRING_SPAWN_X = 135

        const val NINJA_SPAWN_CHANCE = 0.01f
        const val FLY_SPAWN_CHANCE = 0.03f
        const val BULLY_SPAWN_CHANCE = 0.06f

        const val MAX_FRAME_TIME = 0.016f
        const val MOVE_OBJECTS_LINE = 900f
    }
}