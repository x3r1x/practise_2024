<?php

namespace App\Model;

use App\Collision\IVisitor;
use App\Constants\GameConstants;
use App\Constants\JsonConstants;

class Player implements IGameObject, IMoveable
{
    public const SHOOTING_WIDTH = 105.0;
    public const SHOOTING_HEIGHT = 200.0;
    public const RADIUS = 75.0;

    public const DIRECTION_Y_UP = -1;
    public const DIRECTION_Y_DOWN = 1;

    public const DIRECTION_X_LEFT = -1;
    public const DIRECTION_X_RIGHT = 1;

    private const FEET_HEIGHT_RATIO = 0.2;

    public $directionX = self::DIRECTION_X_RIGHT;
    public $directionY = self::DIRECTION_Y_DOWN;
    public $speedY = 0.0;
    public $speedX = 0.0;

    public $isReady = false;

    public $isWinner = false;

    private $x = 0.0;
    private $y = 0.0;

    private $isDisappeared = false;

    public function __construct(
        private int $id,
    ) {
    }

    public function getId(): int
    {
        return $this->id;
    }

    public function jump(float $jumpSpeed = GameConstants::PLAYER_JUMP_SPEED): void
    {
        $this->directionY = self::DIRECTION_Y_UP;
        $this->speedY = $jumpSpeed;
    }

    public function movingThroughScreen(Screen $screen): void
    {
        if ($this->getLeft() < 0.0) {
            $this->x = 0.0 + self::RADIUS;
        }

        if ($this->getRight() > $screen->right) {
            $this->x = $screen->right - self::RADIUS;
        }
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

    public function getFeetTop(): float
    {
        return $this->getBottom() - (self::RADIUS * 2) * self::FEET_HEIGHT_RATIO;
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

        $this->speedY += $elapsedTime * GameConstants::GRAVITY * $this->directionY;

        //echo "Speed: ({$this->speedY})\n";
        // echo "Elapsed time: {$elapsedTime}\n";

        if ($this->y >= $previousY && $this->directionY == self::DIRECTION_Y_UP) {
            $this->directionY = self::DIRECTION_Y_DOWN;
        }
    }

    public function toArray(): array
    {
        return [
            JsonConstants::PLAYER_TYPE,
            round($this->x, 2),
            round($this->y, 2),
            $this->id,
            $this->directionX,
            $this->directionY,
            (int) $this->isWinner,
        ];
    }
}
