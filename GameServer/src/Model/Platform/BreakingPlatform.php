<?php

namespace App\Model\Platform;

use App\Model\Platform;
use App\Constants\JsonConstants;

class BreakingPlatform extends Platform
{
    private const DESTRUCTION_DURATION = 150;
    private const FALLING_DURATION = 2000;

    private $currentFrameIndex;
    private $framesCount;
    private $isBreakRunning;
    private $animationStartTime;
    private $destructionDuration;
    private $fallingDuration;
    private $elapsedDestructionTime;
    private $elapsedFallingTime;
    private $isFalling;
    private $isDestroyed;

    public function __construct($framesCount, $createdX, $createdY)
    {
        parent::__construct($createdX, $createdY);
        $this->currentFrameIndex = 0;
        $this->framesCount = $framesCount;
        $this->isBreakRunning = false;
        $this->destructionDuration = self::DESTRUCTION_DURATION;
        $this->fallingDuration = self::FALLING_DURATION;
        $this->elapsedDestructionTime = 0;
        $this->elapsedFallingTime = 0;
        $this->isFalling = false;
        $this->isDestroyed = false;
    }

    public function runDestructionAnimation($screenHeight)
    {
        if (!$this->isBreakRunning) {
            $this->isBreakRunning = true;
            $this->animationStartTime = $this->getCurrentTime();
            $this->runFrameAnimation();
            $this->runFallingAnimation($screenHeight);
        }
    }

    private function runFrameAnimation()
    {
        while ($this->elapsedDestructionTime < $this->destructionDuration) {
            $currentTime = $this->getCurrentTime();
            $elapsed = $currentTime - $this->animationStartTime;
            $this->elapsedDestructionTime += $elapsed;
            $this->currentFrameIndex = min(floor(($this->elapsedDestructionTime / $this->destructionDuration) * $this->framesCount), $this->framesCount - 1);
            // Logic for frame update would go here
            $this->animationStartTime = $currentTime;
        }
    }

    private function runFallingAnimation($screenHeight)
    {
        if (!$this->isFalling) {
            $this->isFalling = true;
            $this->animationStartTime = $this->getCurrentTime();
        }

        while ($this->elapsedFallingTime < $this->fallingDuration) {
            $currentTime = $this->getCurrentTime();
            $elapsed = $currentTime - $this->animationStartTime;
            $this->elapsedFallingTime += $elapsed;
            $fraction = min($this->elapsedFallingTime / $this->fallingDuration, 1);
            $newY = $this->y + $fraction * ($screenHeight - $this->y);
            $this->setPosition($this->x, $newY);
            $this->animationStartTime = $currentTime;
        }

        $this->isDestroyed = true;
    }

    private function getCurrentTime()
    {
        return round(microtime(true) * 1000);
    }

    public function updateAnimation($elapsedTime)
    {
        //разобраться с анимацией
    }

    public function toArray(): array
    {
        return [
            JsonConstants::BREAKING_PLATFORM_TYPE,
            round($this->x, 2),
            round($this->y, 2),
            0,
            0,
            $this->currentFrameIndex,
        ];
    }
}
