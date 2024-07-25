<?php

namespace App\Model;

use App\Collision\IVisitor;

class Platform implements IGameObject, IMoveable
{
    protected $width = 175.0;
    protected $height = 45.0;

    protected $isDisappeared = false;
    protected $x;
    protected $y;

    protected $id;

    public function __construct(int $id, float $createdX, float $createdY)
    {
        $this->id = $id;

        $this->x = $createdX;
        $this->y = $createdY;
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
        return $this->x - $this->width / 2;
    }

    public function getRight(): float
    {
        return $this->x + $this->width / 2;
    }

    public function getTop(): float
    {
        return $this->y - $this->height / 2;
    }

    public function getBottom(): float
    {
        return $this->y + $this->height / 2;
    }

    public function accept(IVisitor $visitor): void
    {
        $visitor->visitPlatform($this);
    }

    public function getX(): float
    {
        return $this->x;
    }

    public function getY(): float
    {
        return $this->y;
    }

    public function getWidth()
    {
        return $this->width;
    }

    public function getHeight()
    {
        return $this->height;
    }

    public function setPosition(float $startX, float $startY): void
    {
        $this->x = $startX;
        $this->y = $startY;
    }

    public function updatePosition(float $elapsedTime): void
    {
    }

    public function toArray(): array
    {
        return [];
    }
}
