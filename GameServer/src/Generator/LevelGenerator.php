<?php

namespace App\Generator;

use App\Model\EndZone;
use App\Model\Screen;

class LevelGenerator
{
    private Screen $screen;
    private BonusGenerator $bonusGenerator;
    private PlatformGenerator $platformGenerator;

    public function __construct(Screen $screen)
    {
        $this->screen = $screen;
        $this->bonusGenerator = new BonusGenerator;
        $this->platformGenerator = new PlatformGenerator($screen);
    }

    public function updateScreen(Screen $screen): void
    {
        $this->screen = $screen;
    }

    public function generateInitialPack(): array
    {
        $objects = [
            'platform' => [],
            'bonus' => [],
        ];

        $objects['platform'] = $this->platformGenerator->generateInitialPlatforms();

        $this->bonusGenerator->setCounter($this->platformGenerator->getCounter());

        foreach ($objects['platform'] as $platform) {
            ($bonus = $this->bonusGenerator->generateBonus($platform))
                ? $objects['bonus'][] = $bonus
                : null;
        }

        $objects['endZone'][] = new EndZone($this->screen, $objects['platform'][sizeof($objects['platform']) - 1]->getY() - 500);

        return $objects;
    }
}
