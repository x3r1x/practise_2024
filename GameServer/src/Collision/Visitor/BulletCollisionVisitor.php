<?php

namespace App\Collision\Visitor;

use App\Model\Enemy;
use App\Model\Bullet;
use App\Model\Player;
use App\Model\Platform;
use App\Model\Bonus\Shield;
use App\Model\Bonus\Spring;
use App\Collision\IVisitor;

class BulletCollisionVisitor implements IVisitor
{
    public function visitBullet(Bullet $bullet): void
    {
    }

    public function visitEnemy(Enemy $enemy): void
    {
    }

    public function visitPlatform(Platform $platform): void
    {
    }

    public function visitPlayer(Player $player): void
    {
    }

    public function visitShield(Shield $shield): void
    {
    }

    public function visitSpring(Spring $spring): void
    {
    }
}
