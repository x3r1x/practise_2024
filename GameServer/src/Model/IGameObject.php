<?php

namespace App\Model;

use App\Collision\IVisitor;

interface IGameObject
{
    public function getX(): float;
    public function getY(): float;

    public function getIsDisappeared(): bool;
    public function setIsDisappeared(bool $value): void;

    public function getLeft(): float;
    public function getRight(): float;
    public function getTop(): float;
    public function getBottom(): float;

    public function accept(IVisitor $visitor): void;

    public function toArray(): array;
}
