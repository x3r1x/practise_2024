<?php

namespace App\Collision\Visitor;

use App\Model\Enemy;
use App\Model\Screen;
use App\Model\Bullet;
use App\Model\Player;
use App\Model\Platform;
use App\Model\IGameObject;
use App\Model\Enemy\Bully;
use App\Model\Bonus\Shield;
use App\Model\Bonus\Spring;
use App\Collision\IVisitor;
use App\Constants\GameConstants;
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
        if ($this->doesPlayerCollideWithSolid($platform) && !$this->player->isDead) {
            if ($platform instanceof BreakingPlatform) {
                $platform->runDestructionAnimation($this->screen->height);
                return;
            } elseif ($platform instanceof DisappearingPlatform) {
                $platform->animatePlatformColor();
            }
            $this->player->jump();
        }
    }

    public function visitPlayer(Player $player): void
    {
    }

    public function visitShield(Shield $shield): void
    {
        // if ($this->doesPlayerCollideWithCollectable($shield) && !$this->player->isWithShiel && !$this->player->isDead) {
        //     $shield->initPlayer($this->player);
        //     $shield->startDisappearingTimer();
        // }
    }

    public function visitSpring(Spring $spring): void
    {
        // if ($this->doesPlayerCollideWithSolid($spring) && !$this->player->isDead) {
        //     $spring->runStretchAnimation();
        //     $this->player->jump(GameConstants::PLAYER_SPRING_JUMP_SPEED);
        // }
    }

    public function visitEnemy(Enemy $enemy): void
    {
        if ($this->checkCollisionWithEnemy($enemy) && !$this->player->isDead) {
            if ($this->player->directionY == Player::DIRECTION_Y_UP && !$this->player->isWithShield && !$this->player->isWithJetpack) {
                $enemy->killPlayer($this->player);
            } else {
                $enemy->killEnemy();
                $this->player->jump();
            }
        }
    }

    public function visitBullet(Bullet $bullet): void
    {
        // No specific action for visiting a bullet
    }

    private function doesPlayerCollideWithSolid(IGameObject $other): bool
    {
        if ($this->player->isShooting()) {
            return $this->player->getY() + Player::SHOOTING_HEIGHT / 2 < $other->getBottom() && $this->player->getY() + Player::SHOOTING_HEIGHT / 2 >= $other->getTop()
                && ($this->player->getX() - Player::SHOOTING_WIDTH / 2 + 16 < $other->getRight() && $this->player->getX() + Player::SHOOTING_WIDTH / 2 - 16 > $other->getLeft())
                && $this->player->directionY == Player::DIRECTION_Y_DOWN;
        } elseif ($this->player->directionX == Player::DIRECTION_X_RIGHT) {
            return $this->player->getBottom() < $other->getBottom() && $this->player->getBottom() >= $other->getTop() && $this->player->directionY == Player::DIRECTION_Y_DOWN
                && ($this->player->getLeft() + 15 < $other->getRight() && $this->player->getRight() - 50 > $other->getLeft());
        } else {
            return $this->player->getBottom() < $other->getBottom() && $this->player->getBottom() >= $other->getTop() && $this->player->directionY == Player::DIRECTION_Y_DOWN
                && ($this->player->getLeft() + 50 < $other->getRight() && $this->player->getRight() - 15 > $other->getLeft());
        }
    }

    private function doesPlayerCollideWithCollectable(IGameObject $other): bool
    {
        return $this->player->getTop() < $other->getBottom() && $this->player->getBottom() >= $other->getTop()
            && ($this->player->getLeft() < $other->getRight() && $this->player->getRight() > $other->getLeft());
    }

    private function checkCollisionWithEnemy(IGameObject $other): bool
    {
        if (!($other instanceof Bully)) {
            return $this->player->getTop() < $other->getBottom() && $this->player->getBottom() >= $other->getTop()
                && ($this->player->getLeft() < $other->getRight() && $this->player->getRight() > $other->getLeft());
        } else {
            return $this->player->getTop() < $other->getBottom() && $this->player->getBottom() >= $other->getTop()
                && ($this->player->getLeft() < $other->getRight() - GameConstants::BULLY_DEATH_OFFSET_X
                    && $this->player->getRight() > $other->getLeft() + GameConstants::BULLY_DEATH_OFFSET_X);
        }
    }
}
