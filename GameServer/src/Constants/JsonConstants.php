<?php

namespace App\Constants;

class JsonConstants
{
    public const PLAYER_TYPE = 0;
    public const BREAKING_PLATFORM_TYPE = 1;
    public const DISAPPEARING_PLATFORM_TYPE = 2;
    public const MOVING_PLATFORM_ON_X_TYPE = 3;
    public const MOVING_PLATFORM_ON_Y_TYPE = 4;
    public const STATIC_PLATFORM_TYPE = 5;
    public const SPRING_BONUS_TYPE = 7;
    public const END_ZONE_TYPE = 8;

    public const INITIALIZE_MESSAGE = 'i';
    public const MOVE_MESSAGE = 'm';
    public const READY_MESSAGE = 'r';
}
