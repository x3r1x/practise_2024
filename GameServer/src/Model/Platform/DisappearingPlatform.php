<?php

namespace App\Model\Platform;

use App\Model\Platform;
use App\Constants\JsonConstants;

class DisappearingPlatform extends Platform
{
    private const COLOR_CHANGE_DURATION = 2000;
    private const DISAPPEARING_DURATION = 500;

    private $colorChangeDuration;
    private $disappearingDuration;
    private $elapsedColorChangeTime;
    private $elapsedDisappearingTime;
    private $isDisappearing;

    private $animationStartTime;
    private $isRunDestroying = false;

    public function __construct($createdX, $createdY)
    {
        parent::__construct($createdX, $createdY);
        $this->colorChangeDuration = self::COLOR_CHANGE_DURATION;
        $this->disappearingDuration = self::DISAPPEARING_DURATION;
        $this->elapsedColorChangeTime = 0;
        $this->elapsedDisappearingTime = 0;
        $this->isDisappearing = false;
    }

    public function animatePlatform($elapsedTime)
    {
        if (!$this->isRunDestroying) {
            $this->isRunDestroying = true;
            $this->animationStartTime = $this->getCurrentTime();
            $this->runColorChangeAnimation();
        }
    }

    public function animatePlatformColor()
    {
        //изменять цвет от времени
    }

    private function runColorChangeAnimation()
    {
        if ($this->elapsedColorChangeTime < $this->colorChangeDuration) {
            $this->elapsedColorChangeTime += $this->getCurrentTime() - $this->animationStartTime;
            // Logic for color change animation would go here
        } else {
            $this->runDisappearingAnimation();
        }
    }

    private function runDisappearingAnimation()
    {
        if (!$this->isDisappearing) {
            $this->isDisappearing = true;
            $this->animationStartTime = $this->getCurrentTime();
        }

        if ($this->elapsedDisappearingTime < $this->disappearingDuration) {
            $this->elapsedDisappearingTime += $this->getCurrentTime() - $this->animationStartTime;
            // Logic for disappearing animation would go here
        } else {
            $this->isDisappeared = true;
        }
    }

    private function getCurrentTime()
    {
        return round(microtime(true) * 1000);
    }

    public function updateAnimation($elapsedTime)
    {
        if ($this->isRunDestroying) {
            if ($this->isDisappearing) {
                $this->runDisappearingAnimation();
            } else {
                $this->runColorChangeAnimation();
            }
        }
    }

    public function toArray(): array
    {
        return [
            JsonConstants::DISAPPEARING_PLATFORM_TYPE,
            round($this->x, 2),
            round($this->y, 2),
            0,
            0,
            0,
        ];
    }
}
