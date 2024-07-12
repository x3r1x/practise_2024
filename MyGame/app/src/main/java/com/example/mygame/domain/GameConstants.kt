package com.example.mygame.domain

class GameConstants {
    companion object {
        const val GRAVITY = 2200f

        const val SENSOR_MULTIPLIER = 3f

        const val PLAYER_JUMP_SPEED = 1600f
        const val PLAYER_SPRING_JUMP_SPEED = 3700f
        const val PLAYER_SPEED_WITH_JETPACK = 1900f
        const val PLAYER_DISTANCE_TO_TURN = 1f

        const val PLATFORM_ON_X_SPEED = 2f
        const val PLATFORM_ON_Y_SPEED = 2f
        const val PLATFORM_ON_Y_RANGE = 400f

        const val JETPACK_DURATION : Long = 6000 //обязательно больше 3000
        const val SHIELD_DURATION : Long = 10000 //обязательно больше 4000

        const val BULLY_DEATH_OFFSET_X = 60f
        const val FLY_MOVE_SPEED = 4f
        const val NINJA_START_SPEED_X = 4f
        const val NINJA_MIN_SPEED_X = 2
        const val NINJA_MAX_SPEED_X = 10
        const val NINJA_MIN_CONVERGENCE = 10f
        const val NINJA_MAX_CONVERGENCE = 600f

        const val PLATFORM_SPAWN_ADDITIONAL_X = 100f

        const val JETPACK_SPAWN_CHANCE = 0.02f
        const val SHIELD_SPAWN_CHANCE = 0.08f
        const val SPRING_SPAWN_CHANCE = 0.25f
        const val MIN_SPRING_SPAWN_X = 40
        const val MAX_SPRING_SPAWN_X = 180

        const val BULLY_SPAWN_CHANCE = 0.03f
        const val NINJA_SPAWN_CHANCE = 0.05f
        const val FLY_SPAWN_CHANCE = 0.1f

        const val MAX_FRAME_TIME = 0.016f
        const val MOVE_OBJECTS_LINE = 900f
    }
}