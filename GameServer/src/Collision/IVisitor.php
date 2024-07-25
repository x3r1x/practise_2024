<?php

namespace App\Collision;

use App\Model\Enemy;
use App\Model\Bullet;
use App\Model\Player;
use App\Model\Platform;
use App\Model\Bonus\Shield;
use App\Model\Bonus\Spring;
use App\Model\EndZone;

interface IVisitor
{
    function visitPlatform(Platform $platform): void;
    function visitPlayer(Player $player): void;
    function visitSpring(Spring $spring): void;
    function visitEndZone(EndZone $endZone): void;
}
