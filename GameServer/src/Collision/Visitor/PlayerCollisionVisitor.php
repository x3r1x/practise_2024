<?php

namespace App\Collision\Visitor;

use App\Model\Screen;
use App\Model\Player;
use App\Model\Platform;
use App\Model\IGameObject;
use App\Model\Bonus\Spring;
use App\Collision\IVisitor;
use App\Constants\GameConstants;
use App\Model\EndZone;
use App\Model\Platform\BreakingPlatform;
use App\Model\Platform\DisappearingPlatform;

class PlayerCollisionVisitor implements IVisitor
{
    private ?Player $player = null;
    private Screen $screen;

    public function __construct(Screen $screen)
    {
        $this->screen = $screen;
    }

    public function setPlayer(Player $player): void
    {
        $this->player = $player;
    }

    public function visitPlatform(Platform $platform): void
    {
        if ($this->doesPlayerCollideWithSolid($platform)) {
            $this->player->jump();
        }
    }

    public function visitPlayer(Player $player): void
    {
    }

    public function visitSpring(Spring $spring): void
    {
        if ($this->doesPlayerCollideWithSolid($spring)) {
            $this->player->jump(GameConstants::PLAYER_SPRING_JUMP_SPEED);
        }
    }

    public function visitEndZone(EndZone $endZone): void
    {
        if ($this->player->getBottom() < $endZone->getBottom()) {
            echo "Game End\n";
            $this->player->isWinner = true;
            $endZone->setIsActive(true);
        }
    }

    private function doesPlayerCollideWithSolid(IGameObject $other): bool
    {
        return $this->player->getFeetTop() < $other->getBottom() && $this->player->getBottom() >= $other->getTop()
            && ($this->player->getLeft() < $other->getRight() && $this->player->getRight() > $other->getLeft())
            && $this->player->directionY == Player::DIRECTION_Y_DOWN;
    }
}
