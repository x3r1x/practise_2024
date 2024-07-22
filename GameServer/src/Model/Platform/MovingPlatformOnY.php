<?php

namespace App\Model\Platform;

use App\Model\Platform;
use App\Constants\GameConstants;
use App\Constants\JsonConstants;

class MovingPlatformOnY extends Platform
{
    private $minY;
    private $maxY;
    private $directionY;

    private $speedY;

    public function __construct($createdX, $createdY)
    {
        parent::__construct($createdX, $createdY);
        $this->minY = $createdY - GameConstants::PLATFORM_ON_Y_RANGE;
        $this->maxY = $createdY + GameConstants::PLATFORM_ON_Y_RANGE;
        $this->directionY = $this->randomDirectionY();
    }

    const DIRECTION_UP = 0;
    const DIRECTION_DOWN = 1;

    private function randomDirectionY()
    {
        $value = rand(self::DIRECTION_UP, self::DIRECTION_DOWN);
        return $value;
    }

    private function updateDirection()
    {
        if ($this->y <= $this->minY) {
            $this->directionY = self::DIRECTION_DOWN;
        } else if ($this->y + $this->height >= $this->maxY) {
            $this->directionY = self::DIRECTION_UP;
        }
    }

    public function setPosition($startX, $startY): void
    {
        parent::setPosition($startX, $startY);
        $this->minY = $startY - GameConstants::PLATFORM_ON_Y_RANGE;
        $this->maxY = $startY + GameConstants::PLATFORM_ON_Y_RANGE;
    }

    public function updatePosition($elapsedTime): void
    {
        if ($elapsedTime > 1.0) {
            return;
        }
        $this->updateDirection();
        $this->speedY = GameConstants::PLATFORM_ON_Y_SPEED * $elapsedTime;

        if ($this->directionY == self::DIRECTION_UP) {
            $this->y -= $this->speedY;
        } else if ($this->directionY == self::DIRECTION_DOWN) {
            $this->y += $this->speedY;
        }
    }

    public function toArray(): array
    {
        return [
            JsonConstants::MOVING_PLATFORM_ON_Y_TYPE,
            round($this->x, 2),
            round($this->y, 2),
            0,
            round($this->speedY, 2),
            0,
        ];
    }
}
