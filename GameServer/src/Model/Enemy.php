<?php

namespace App\Model;

use App\Model\Player;

class Enemy implements IGameObject
{
    public $width = 0.0;
    public $height = 0.0;


    public $x;
    public $y;

    private $isDead = false;

    private $isDisappeared = false;

    public function __construct(float $createdX, float $createdY)
    {
        $this->x = $createdX;
        $this->y = $createdY;
    }

    public function getLeft(): float
    {
        return $this->x - $this->width / 2;
    }

    public function getBottom(): float
    {
        return $this->y + $this->height / 2;
    }

    public function getRight(): float
    {
        return $this->x + $this->width / 2;
    }

    public function getTop(): float
    {
        return $this->y - $this->height / 2;
    }

    public function setPosition(float $startX, float $startY): void
    {
        $this->x = $startX;
        $this->y = $startY;
    }

    public function killPlayer(Player $player): void
    {
        if (!$this->isDead) {
            $player->directionY = Player::DIRECTION_Y_DOWN;
            $player->speedY = 0.0;
            $player->isDead = true;
        }
    }

    public function killEnemy(): void
    {
        $this->isDead = true;
        $this->runDeathAnimation();
    }

    private function runDeathAnimation(): void
    {
        $deathTime = 2000;
    }

    public function accept(\App\Collision\IVisitor $visitor): void
    {
    }

    public function getIsDisappeared(): bool
    {
        return $this->isDisappeared;
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
