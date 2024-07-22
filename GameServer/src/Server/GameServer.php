<?php

namespace App\Server;

use App\Model\Player;
use App\Model\Screen;
use Ratchet\ConnectionInterface;
use App\Collision\CollisionHandler;
use Ratchet\MessageComponentInterface;
use App\Model\Platform\StaticPlatform;
use App\Model\Platform\BreakingPlatform;
use App\Model\Platform\MovingPlatformOnX;
use App\Model\Platform\MovingPlatformOnY;
use App\Model\Platform\DisappearingPlatform;

class GameServer implements MessageComponentInterface
{
    public float $lastUpdateTime = 0.0;

    private $clients;

    private Screen $screen;
    private array $gameState;
    private float $deltaTime = 0.0;
    private CollisionHandler $collisionHandler;

    public function __construct()
    {
        $this->clients = new \SplObjectStorage;

        //получать данные от пользователя
        $this->screen = new Screen(900, 2000);

        //переделать на классы
        $this->gameState = [
            'player' => [],
            'platform' => [],
            'enemy' => [],
            'bonus' => [],
            'bullet' => [],
        ];

        $this->collisionHandler = new CollisionHandler($this->gameState, $this->screen);
    }

    public function testScene(): void
    {
        $this->gameState['platform'][] = new StaticPlatform(0, 1700);
        $this->gameState['platform'][] = new StaticPlatform(200, 1000);
        $this->gameState['platform'][] = new StaticPlatform(300, 900);
        $this->gameState['platform'][] = new BreakingPlatform(0, 500, 1500);
        $this->gameState['platform'][] = new DisappearingPlatform(300, 1300);
        $this->gameState['platform'][] = new MovingPlatformOnX(400, 1400);
        $this->gameState['platform'][] = new MovingPlatformOnY(700, 1500);
    }

    public function onOpen(ConnectionInterface $conn)
    {
        $this->clients->attach($conn);
        $playerId = $conn->resourceId;
        $this->gameState['player'][$playerId] = new Player($playerId);
        echo "New connection! ({$conn->resourceId})\n";
    }

    public function onMessage(ConnectionInterface $from, $msg)
    {
        $data = json_decode($msg, true);

        if (isset($data['dx']) && isset($data['tap'])) {
            $playerId = $from->resourceId;
            if (isset($this->gameState['player'][$playerId])) {
                $this->gameState['player'][$playerId]->updatePositionX($data['dx'], $this->deltaTime);
            }
            if ($data['tap'] === true) {
                $this->gameState['player'][$playerId]->shoot();
            }
        }
    }

    public function onClose(ConnectionInterface $conn)
    {
        $this->clients->detach($conn);
        unset($this->gameState['player'][$conn->resourceId]);
        echo "Connection {$conn->resourceId} has disconnected\n";
    }

    public function onError(ConnectionInterface $conn, \Exception $e)
    {
        echo "An error has occurred: {$e->getMessage()}\n";
        $conn->close();
    }

    public function updateGameState(float $deltaTime)
    {
        $this->deltaTime = $deltaTime;

        foreach ($this->gameState as $key => $element) {
            foreach ($element as $key => $object) {
                $object->updatePosition($deltaTime);
            }
        }

        $this->collisionHandler->checkCollisions($this->gameState);
    }

    public function sendData(): void
    {
        $gameData = [
            'obj' => [],
            's' => 'r'
        ];

        foreach ($this->gameState as $key => $element) {
            foreach ($element as $key => $object) {
                $gameData['obj'][] = $object->toArray();
            }
        }

        $jsonGameData = json_encode($gameData);
        foreach ($this->clients as $client) {
            $client->send($jsonGameData);
        }
    }
}
