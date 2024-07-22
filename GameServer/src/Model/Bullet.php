<?php

namespace App\Model;

use App\Collision\IVisitor;

class Bullet implements IMoveable, IGameObject
{
    private const DEFAULT_ACCELERATION = 2200.0;
    private const RADIUS = 50.0;

    private $x;
    private $y;
    private $angle;
    private $isDisappeared = false;
    private $speedY = 0;

    public function __construct(float $x, float $y, float $angle)
    {
        $this->x = $x;
        $this->y = $y;
        $this->angle = $angle;
    }

    public function shoot(): void
    {
        $this->speedY = self::DEFAULT_ACCELERATION;
    }

    public function setPosition(float $startX, float $startY): void
    {
        $this->x = $startX;
        $this->y = $startY;
    }

    public function updatePosition(float $elapsedTime): void
    {
        $this->y -= $this->speedY * $elapsedTime;
        $this->x += $this->angle;
    }

    public function left(): float
    {
        return $this->x - self::RADIUS / 2;
    }

    public function right(): float
    {
        return $this->x + self::RADIUS / 2;
    }

    public function top(): float
    {
        return $this->y - self::RADIUS / 2;
    }

    public function bottom(): float
    {
        return $this->y + self::RADIUS / 2;
    }

    public function accept(IVisitor $visitor): void
    {
        $visitor->visitBullet($this);
    }

    public function getX(): float
    {
        return $this->x;
    }


    public function getY(): float
    {
        return $this->y;
    }


    public function getBottom(): float
    {
        return $this->y + self::RADIUS;
    }


    public function getIsDisappeared(): bool
    {
        return $this->isDisappeared;
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


    public function setIsDisappeared(bool $value): void
    {
        $this->isDisappeared = $value;
    }


    public function toArray(): array
    {
        return [];
    }
}
