<?php

namespace App\Model\Bonus;

use App\Model\Platform;
use App\Model\IMoveable;
use App\Model\IGameObject;
use App\Collision\IVisitor;
use App\Constants\JsonConstants;

class Spring implements IMoveable, IBonus, IGameObject
{
    private const WIDTH = 78.0;
    private const HEIGHT = 53.0;

    private float $x;
    private float $y;

    private int $id;

    private float $left;
    private float $right;
    private float $top;
    private float $bottom;

    private bool $isDisappeared = false;

    public function __construct(Platform $platform, $id)
    {
        $this->id = $id;

        $this->x = $platform->getX();
        $this->y = $platform->getTop() - self::HEIGHT / 2;

        $this->left = $this->x - self::WIDTH / 2;
        $this->right = $this->x + self::WIDTH / 2;
        $this->top = $this->y - self::HEIGHT / 2;
        $this->bottom = $this->y + self::HEIGHT / 2;
    }

    public function setPosition(float $startX, float $startY): void
    {
        $this->x = $startX;
        $this->y = $startY;

        $this->left = $this->x - self::WIDTH / 2;
        $this->right = $this->x + self::WIDTH / 2;
        $this->top = $this->y - self::HEIGHT / 2;
        $this->bottom = $this->y + self::HEIGHT / 2;
    }

    public function accept(IVisitor $visitor): void
    {
        $visitor->visitSpring($this);
    }

    public function getX(): float
    {
        return $this->x;
    }

    public function getY(): float
    {
        return $this->y;
    }

    public function getLeft(): float
    {
        return $this->left;
    }

    public function getRight(): float
    {
        return $this->right;
    }

    public function getTop(): float
    {
        return $this->top;
    }

    public function getBottom(): float
    {
        return $this->bottom;
    }

    public function getIsDisappeared(): bool
    {
        return $this->isDisappeared;
    }

    public function setIsDisappeared(bool $value): void
    {
        $this->isDisappeared = $value;
    }

    public function updatePosition(float $elapsedTime): void
    {
    }

    public function toArray(): array
    {
        return [
            JsonConstants::SPRING_BONUS_TYPE,
            $this->x,
            $this->y,
            $this->id,
            0,
        ];
    }
}
