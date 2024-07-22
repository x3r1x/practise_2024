<?php

namespace App\Model;

use App\Constants\GameConstants;

class Screen
{
    public float $left;
    public float $right;
    public float $top;
    public float $bottom;

    public float $maxPlayerHeight;

    public function __construct(
        public float $width,
        public float $height,
    ) {
        $this->left = 0.0;
        $this->right = $width;
        $this->top = 0.0;
        $this->bottom = $height;

        $this->maxPlayerHeight = $height - GameConstants::MOVE_OBJECTS_LINE;
    }
}