<?php

namespace App\Model;

use App\Collision\IVisitor;
use App\Constants\GameConstants;
use App\Constants\JsonConstants;

class Player implements IGameObject, IMoveable
{
    const SHOOTING_WIDTH = 105.0;
    const SHOOTING_HEIGHT = 200.0;
    const RADIUS = 75.0;
    const DEAD_WIDTH = 120.0;
    const DEAD_HEIGHT = 190.0;

    const DIRECTION_Y_UP = -1;
    const DIRECTION_Y_DOWN = 1;

    const DIRECTION_X_LEFT = -1;
    const DIRECTION_X_RIGHT = 1;

    public $directionX = self::DIRECTION_X_RIGHT;
    public $directionY = self::DIRECTION_Y_DOWN;
    public $speedY = 0.0;
    public $speedX = 0.0;
    public $isDead = false;

    public $isWithJetpack = false;
    public $isWithShield = false;
    private $isShooting = false;

    private $x = 0.0;
    private $y = 0.0;

    private $isDisappeared = false;

    public function __construct(
        private int $id,
    ) {
    }

    public function jump(float $jumpSpeed = GameConstants::PLAYER_JUMP_SPEED): void
    {
        $this->directionY = self::DIRECTION_Y_UP;
        $this->speedY = $jumpSpeed;
    }

    public function movingThroughScreen(Screen $screen): void
    {
        if ($this->x < 0.0) {
            $this->x = $screen->right - self::RADIUS;
        }

        if ($this->x > $screen->right) {
            $this->x = 0.0 + self::RADIUS;
        }
    }

    public function isShooting(): bool
    {
        return $this->isShooting;
    }

    public function shoot(): void
    {
        $this->isShooting = true;

        $this->isShooting = false;
    }

    public function updatePositionX(float $deltaX, float $elapsedTime): void
    {
        $this->speedX = $deltaX * $elapsedTime;
        $this->x += $this->speedX;
        $this->changeDirectionX($deltaX);
    }

    private function changeDirectionX(float $newX): void
    {
        if ($newX < -GameConstants::PLAYER_DISTANCE_TO_TURN) {
            $this->directionX = self::DIRECTION_X_LEFT;
        } else if ($newX > GameConstants::PLAYER_DISTANCE_TO_TURN) {
            $this->directionX = self::DIRECTION_X_RIGHT;
        }
    }

    public function accept(IVisitor $visitor): void
    {
        $visitor->visitPlayer($this);
    }

    public function getIsDisappeared(): bool
    {
        return $this->isDisappeared;
    }

    public function setIsDisappeared(bool $value): void
    {
        $this->isDisappeared = $value;
    }

    public function getLeft(): float
    {
        return $this->x - self::RADIUS;
    }

    public function getRight(): float
    {
        return $this->x + self::RADIUS;
    }

    public function getTop(): float
    {
        return $this->y - self::RADIUS;
    }

    public function getBottom(): float
    {
        return $this->y + self::RADIUS;
    }

    public function getX(): float
    {
        return $this->x;
    }

    public function getY(): float
    {
        return $this->y;
    }

    public function setPosition(float $startX, float $startY): void
    {
        $this->x = $startX;
        $this->y = $startY;
    }

    public function updatePosition(float $elapsedTime): void
    {
        $previousY = $this->y;

        $this->y += $this->speedY * $this->directionY * $elapsedTime;

        if (!$this->isWithJetpack) {
            $this->speedY += $elapsedTime * GameConstants::GRAVITY * $this->directionY;

            if ($this->y >= $previousY && $this->directionY == self::DIRECTION_Y_UP) {
                $this->directionY = self::DIRECTION_Y_DOWN;
            }
        }
    }

    public function toArray(): array
    {
        return [
            JsonConstants::PLAYER_TYPE,
            round($this->x, 2),
            round($this->y, 2),
            round($this->speedX, 2),
            round($this->speedY, 2),
            $this->id,
            $this->directionX,
            $this->directionY,
            (int) $this->isWithShield,
            (int) $this->isShooting,
            (int) $this->isDead,
        ];
    }
}
