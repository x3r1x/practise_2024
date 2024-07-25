<?php

namespace App\Collision;

use App\Model\Player;
use App\Model\Screen;
use App\Collision\Visitor\ScreenCollisionVisitor;
use App\Collision\Visitor\PlayerCollisionVisitor;

class CollisionHandler
{
    private array $gameState;

    private Screen $screen;

    private PlayerCollisionVisitor $playerCollisionVisitor;

    private ScreenCollisionVisitor $screenCollisionVisitor;

    public function __construct(Screen $screen)
    {
        $this->gameState = [
            'player' => [],
            'platform' => [],
            'bonus' => [],
            'endZone' => [],
        ];

        $this->playerCollisionVisitor = new PlayerCollisionVisitor($screen);
        $this->screenCollisionVisitor = new ScreenCollisionVisitor($screen);
    }

    public function setState(array $newState): void
    {
        $this->gameState = $newState;
    }

    public function updateScreen(Screen $screen)
    {
        $this->screen = $screen;
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
        if (isset($this->gameState['player'], $this->gameState['platform'], $this->gameState['bonus'])) {
            $objects = array_merge($this->gameState['player'], $this->gameState['platform'], $this->gameState['bonus']);
            foreach ($objects as $object) {
                $object->accept($this->screenCollisionVisitor);
            }
        }
    }

    private function checkPlayerCollision(Player $player): void
    {
        $this->playerCollisionVisitor->setPlayer($player);
        if (isset($this->gameState['player'], $this->gameState['platform'], $this->gameState['bonus'], $this->gameState['endZone'])) {
            $objects = array_merge($this->gameState['player'], $this->gameState['platform'], $this->gameState['bonus'], $this->gameState['endZone']);
            foreach ($objects as $object) {
                if (!$object instanceof Player) {
                    $object->accept($this->playerCollisionVisitor);
                }
            }
        }
    }
}
