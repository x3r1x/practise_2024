<?php

namespace App\Logic;

class Timer
{
    private $lastCallTime;
    private $accumulatedTime;
    private $interval;

    public function __construct($interval)
    {
        $this->lastCallTime = microtime(true);
        $this->accumulatedTime = 0;
        $this->interval = $interval;
    }

    public function checkAndUpdate()
    {
        $currentTime = microtime(true);
        $delta = $currentTime - $this->lastCallTime;
        $this->lastCallTime = $currentTime;

        $this->accumulatedTime += $delta;

        if ($this->accumulatedTime >= $this->interval) {
            // Время обновления состояния игры
            $this->accumulatedTime -= $this->interval;
            return true;
        }

        return false;
    }
}
