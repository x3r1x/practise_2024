<?php

namespace App\Constants;

class GameConstants
{
    public const GRAVITY = 2200.0;

    public const SENSOR_MULTIPLIER = 180.0;

    public const PLAYER_JUMP_SPEED = 1600.0;
    public const PLAYER_SPRING_JUMP_SPEED = 3700.0;
    public const PLAYER_SPEED_WITH_JETPACK = 1900.0;
    public const PLAYER_DISTANCE_TO_TURN = 1.5;

    public const PLATFORM_ON_X_SPEED = 200.0;
    public const PLATFORM_ON_Y_SPEED = 200.0;
    public const PLATFORM_ON_Y_RANGE = 400.0;

    public const JETPACK_DURATION = 6000; // должно быть больше 3000
    public const SHIELD_DURATION = 10000; // должно быть больше 4000

    public const BULLY_DEATH_OFFSET_X = 60.0;
    public const FLY_MOVE_SPEED = 520.0;
    public const NINJA_START_SPEED_X = 300.0;
    public const NINJA_MIN_SPEED_X = 130;
    public const NINJA_MAX_SPEED_X = 650;
    public const NINJA_MIN_CONVERGENCE = 10.0;
    public const NINJA_MAX_CONVERGENCE = 600.0;

    public const PLATFORM_SPAWN_ADDITIONAL_X = 100.0;

    public const JETPACK_SPAWN_CHANCE = 0.02;
    public const SHIELD_SPAWN_CHANCE = 0.08;
    public const SPRING_SPAWN_CHANCE = 0.25;
    public const MIN_SPRING_SPAWN_X = 40;
    public const MAX_SPRING_SPAWN_X = 135;

    public const BULLY_SPAWN_CHANCE = 0.03;
    public const NINJA_SPAWN_CHANCE = 0.01;
    public const FLY_SPAWN_CHANCE = 0.1;

    public const MAX_FRAME_TIME = 0.016;
    public const MOVE_OBJECTS_LINE = 900.0;
}