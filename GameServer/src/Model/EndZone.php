<?php

namespace App\Model;

use App\Collision\IVisitor;
use App\Constants\JsonConstants;

class EndZone implements IGameObject
{
    private const HEIGHT = 100.0;

    private float $x;
    private float $y;

    private float $width;

    private bool $isDisappeared;

    private bool $isActive;

    public function __construct(Screen $screen, float $y)
    {
        $this->y = $y;
        $this->x = $screen->width / 2;
        $this->width = $screen->width;
        $this->isDisappeared = false;
        $this->isActive = false;
    }

    public function accept(IVisitor $visitor): void
    {
        $visitor->visitEndZone($this);
    }

    public function getIsDisappeared(): bool
    {
        return $this->isDisappeared;
    }

    public function setIsDisappeared(bool $value): void
    {
        $this->isDisappeared = $value;
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
        return $this->y + self::HEIGHT / 2;
    }

    public function getLeft(): float
    {
        return $this->y - self::HEIGHT / 2;
    }

    public function getRight(): float
    {
        return $this->x + $this->width / 2;
    }

    public function getTop(): float
    {
        return $this->x - $this->width / 2;
    }

    public function getIsActive(): bool
    {
        return $this->isActive;
    }

    public function setWidth(float $value): void
    {
        $this->width = $value;
    }

    public function setIsActive(bool $value): void
    {
        $this->isActive = $value;
    }

    public function updatePosition(): void
    {
    }

    public function toArray(): array
    {
        return [
            JsonConstants::END_ZONE_TYPE,
            $this->x,
            $this->y,
        ];
    }
}
