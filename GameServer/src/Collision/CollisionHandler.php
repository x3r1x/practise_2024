<?php

namespace App\Collision;

use App\Model\Player;
use App\Model\Screen;
use App\Collision\Visitor\ScreenCollisionVisitor;
use App\Collision\Visitor\PlayerCollisionVisitor;

class CollisionHandler
{
    private $gameState;

    private PlayerCollisionVisitor $playerCollisionVisitor;

    private ScreenCollisionVisitor $screenCollisionVisitor;

    public function __construct(array $gameState, Screen $screen)
    {
        $this->gameState = $gameState;
        $this->playerCollisionVisitor = new PlayerCollisionVisitor($screen);
        $this->screenCollisionVisitor = new ScreenCollisionVisitor($screen);
    }

    public function setState(array $newState): void
    {
        $this->gameState = $newState;
    }

    public function checkCollisions(array $gameState): void
    {
        $this->setState($gameState);

        $this->checkScreenCollision();

        foreach ($this->gameState['player'] as $player) {
            $this->checkPlayerCollision($player);
        }
    }

    private function checkScreenCollision(): void
    {
        foreach (array_merge($this->gameState['player'], $this->gameState['platform'], $this->gameState['enemy'], $this->gameState['bonus']) as $object) {
            $object->accept($this->screenCollisionVisitor);
        }
    }

    private function checkPlayerCollision(Player $player): void
    {
        $this->playerCollisionVisitor->setPlayer($player);
        foreach (array_merge($this->gameState['player'], $this->gameState['platform'], $this->gameState['enemy'], $this->gameState['bonus']) as $object) {
            if (!$object instanceof Player) {
                $object->accept($this->playerCollisionVisitor);
            }
        }
    }
}
