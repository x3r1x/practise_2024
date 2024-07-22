<?php

namespace App\Generator;

class LevelGenerator
{
    private EnemyGenerator $enemyGenerator;
    private BonusGenerator $bonusGenerator;
    private PlatformGenerator $platformGenerator;

    public function __construct()
    {
        $this->enemyGenerator = new EnemyGenerator;
        $this->bonusGenerator = new BonusGenerator;
        $this->platformGenerator = new PlatformGenerator;
    }

    
}
