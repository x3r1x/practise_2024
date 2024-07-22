<?php

namespace App\Model;

interface IMoveable {
    public function getX(): float;
    public function getY(): float;

    public function setPosition(float $startX, float $startY): void;
    public function updatePosition(float $elapsedTime): void;
}