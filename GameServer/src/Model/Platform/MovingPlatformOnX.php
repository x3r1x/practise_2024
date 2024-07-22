<?php

namespace App\Model\Platform;

use App\Model\Screen;
use App\Model\Platform;
use App\Constants\JsonConstants;
use App\Constants\GameConstants;

class MovingPlatformOnX extends Platform
{
    public const DIRECTION_LEFT = 0;
    public const DIRECTION_RIGHT = 1;

    private $directionX;

    private $speedX;

    public function __construct($createdX, $createdY)
    {
        parent::__construct($createdX, $createdY);
        $this->directionX = $this->directionXRandom();
    }

    public function changeDirectionX(Screen $screen)
    {
        if ($this->getRight() >= $screen->right) {
            $this->directionX = self::DIRECTION_LEFT;
        } else if ($this->x <= $screen->left) {
            $this->directionX = self::DIRECTION_RIGHT;
        }
    }

    private function directionXRandom()
    {
        $value = rand(self::DIRECTION_LEFT, self::DIRECTION_RIGHT);
        return $value;
    }

    public function updatePosition($elapsedTime): void
    {
        if ($elapsedTime > 1.0) {
            return;
        }
        $this->speedX = GameConstants::PLATFORM_ON_X_SPEED * $elapsedTime;

        if ($this->directionX == self::DIRECTION_LEFT) {
            $this->x -= $this->speedX;
        } else {
            $this->x += $this->speedX;
        }
    }

    public function toArray(): array
    {
        return [
            JsonConstants::MOVING_PLATFORM_ON_X_TYPE,
            round($this->x, 2),
            round($this->y, 2),
            round($this->speedX, 2),
            0,
            0,
        ];
    }
}
