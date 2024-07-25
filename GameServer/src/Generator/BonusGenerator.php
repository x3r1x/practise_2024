<?php

namespace App\Generator;

use App\Model\Platform;
use App\Model\Bonus\IBonus;
use App\Model\Bonus\Shield;
use App\Model\Bonus\Spring;
use App\Constants\GameConstants;
use App\Model\Platform\StaticPlatform;

class BonusGenerator
{
    private int $counter = 0;


    public function generateBonus(Platform $platform): ?IBonus
    {
        if (!$platform instanceof StaticPlatform) {
            return null;
        }

        $bonus = null;
        $random = mt_rand() / mt_getrandmax();

        if ($random < GameConstants::SPRING_SPAWN_CHANCE) {
            $bonus = new Spring($platform, $this->counter);
            $this->counter++;
        }

        return $bonus;
    }

    public function setCounter(int $counter): void
    {
        $this->counter = $counter;
    }
}
