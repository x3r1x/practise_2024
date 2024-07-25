<?php

namespace App\Generator;

use App\Model\Screen;
use App\Model\Platform;
use App\Model\Platform\StaticPlatform;

class PlatformGenerator
{
    private $screen;
    private $platformGap;
    private $newPackageHeight;
    private $platform;

    private $counter;

    public function __construct(Screen $screen)
    {
        $this->counter = 0;
        $this->screen = $screen;
        $this->platformGap = 50.0;
        $this->newPackageHeight = 5000.0;
        $this->platform = new Platform(0, 0, 0);
    }

    public function generateInitialPlatforms(): array
    {
        $newY = $this->screen->height;
        $platforms = [];
        $this->generateBottomPlatforms($platforms);

        while ($newY >= -$this->newPackageHeight) {
            $x = mt_rand($this->platform->getWidth(), $this->screen->width - $this->platform->getWidth());

            $newPlatform = new StaticPlatform($this->counter, $x, $newY);
            $platforms[] = $newPlatform;

            $randomGap = mt_rand($this->platformGap, 250);
            $newY -= $this->platform->getHeight() + $randomGap;

            $this->counter++;
        }

        return $platforms;
    }

    public function getCounter()
    {
        return $this->counter;
    }

    public function setCounter($counter): void
    {
        $this->counter = $counter;
    }

    private function generateBottomPlatforms(&$platforms): void
    {
        $platforms = [
            new StaticPlatform($this->counter, 0, $this->screen->height + 300),
            new StaticPlatform($this->counter + 1, 300, $this->screen->height + 300),
            new StaticPlatform($this->counter + 2, 600, $this->screen->height + 300),
            new StaticPlatform($this->counter + 3, 900, $this->screen->height + 300),
            new StaticPlatform($this->counter + 4, 1200, $this->screen->height + 300),
        ];
        $this->counter += 5;
    }
}
