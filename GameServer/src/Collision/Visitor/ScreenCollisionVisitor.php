<?php

namespace App\Collision\Visitor;

use App\Model\Enemy;
use App\Model\Bullet;
use App\Model\Player;
use App\Model\Screen;
use App\Model\Platform;
use App\Model\Bonus\Shield;
use App\Model\Bonus\Spring;
use App\Collision\IVisitor;
use App\Model\EndZone;
use App\Model\Platform\MovingPlatformOnX;

class ScreenCollisionVisitor implements IVisitor
{
    private $screen;

    public function __construct(Screen $screen)
    {
        $this->screen = $screen;
    }

    public function visitPlayer(Player $player): void
    {
        if ($this->isCollidePlayerWithScreen($player)) {
            $player->movingThroughScreen($this->screen);
        }
    }

    public function visitPlatform(Platform $platform): void
    {
        if ($this->isOutPlatformBelowBottomOfScreen($platform)) {
            $platform->setIsDisappeared(true);
        }
    }

    public function visitSpring(Spring $spring): void
    {
    }

    public function visitEndZone(EndZone $endZone): void {
    }

    private function isCollidePlayerWithScreen(Player $player): bool
    {
        return $player->getX() < $this->screen->left || $player->getX() > $this->screen->right;
    }

    private function isCollidePlatformWithBoundsOfScreen(Platform $platform): bool
    {
        return $platform->getRight() >= $this->screen->right || $platform->getLeft() <= $this->screen->left;
    }

    private function isOutPlatformBelowBottomOfScreen(Platform $platform): bool
    {
        return $platform->getTop() > $this->screen->bottom;
    }
}
