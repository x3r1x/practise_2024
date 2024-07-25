<?php

namespace App\Logic;

class Timer
{
    private $lastCallTime;
    private $accumulatedTime;
    private $interval;
    private $running = false;

    public function __construct($interval)
    {
        $this->interval = $interval;
    }

    public function start()
    {
        if (!$this->running) {
            $this->lastCallTime = microtime(true);
            $this->accumulatedTime = 0;
            $this->running = true;
        }
    }

    public function stop()
    {
        $this->running = false;
    }

    public function checkAndUpdate()
    {
        if (!$this->running) {
            return false;
        }

        $currentTime = microtime(true);
        $delta = $currentTime - $this->lastCallTime;
        $this->lastCallTime = $currentTime;

        $this->accumulatedTime += $delta;

        if ($this->accumulatedTime >= $this->interval) {
            $this->accumulatedTime -= $this->interval;
            return true;
        }

        return false;
    }
}
