<?php

namespace App\Server;

use App\Model\Player;
use App\Model\Screen;
use Ratchet\ConnectionInterface;
use App\Constants\JsonConstants;
use App\Generator\LevelGenerator;
use App\Collision\CollisionHandler;
use Ratchet\MessageComponentInterface;

class GameServer implements MessageComponentInterface
{
    private const LOBBY = 'l';
    private const RUNNING = 'r';
    private const END = 'e';

    private const ERROR_MESSAGE = '{"e": 1}';

    public ?float $lastUpdateTime = null;

    public bool $isRunning = false;

    private $clients;

    private int $counter = 0;

    private bool $canGenerateLevel = true;

    private string $state;

    private Screen $screen;
    private array $gameState;
    private float $deltaTime = 0.0;
    private CollisionHandler $collisionHandler;

    private LevelGenerator $levelGenerator;

    public function __construct()
    {
        $this->clients = new \SplObjectStorage;

        $this->screen = new Screen(1000, 2000);

        $this->gameState = [
            'player' => [],
        ];

        $this->state = self::LOBBY;

        $this->levelGenerator = new LevelGenerator($this->screen);
        $this->collisionHandler = new CollisionHandler($this->screen);
    }

    public function onOpen(ConnectionInterface $conn)
    {
        $this->clients->attach($conn);

        if ($this->isRunning || $this->state === self::END) {
            $conn->send(self::ERROR_MESSAGE);
            $conn->close();
            return;
        }

        $playerId = $conn->resourceId;
        $this->gameState['player'][$playerId] = new Player($playerId);

        $conn->send("{\"id\": $playerId}");

        $this->setStartPositions();

        $this->sendGameState();

        echo "New connection! ({$playerId})\n";
    }

    public function onMessage(ConnectionInterface $from, $msg)
    {
        $this->handleMessage($from, $msg);
    }

    public function onClose(ConnectionInterface $player)
    {
        $this->disconnectPlayer($player);

        if (sizeof($this->gameState['player']) === 0) {
            $this->restart();
        }
    }

    public function onError(ConnectionInterface $player, \Exception $e)
    {
        echo "An error has occurred: {$e->getMessage()}\n";
        $player->close();
    }

    public function updateGameState(float $deltaTime)
    {
        $this->counter++;
        if ($this->counter == 2) {
            $this->sendMovingObjects();
            $this->counter = 0;
        }

        $this->deltaTime = $deltaTime;

        foreach ($this->gameState as $key => $element) {
            foreach ($element as $key => $object) {
                $object->updatePosition($deltaTime);
            }
        }

        $this->collisionHandler->checkCollisions($this->gameState);

        $this->tryEndGame();
    }

    public function sendGameState(): void
    {
        $gameData = [
            'obj' => [],
            's' => $this->state,
        ];

        foreach ($this->gameState as $key => $element) {
            foreach ($element as $key => $object) {
                $gameData['obj'][] = $object->toArray();
            }
        }

        $this->sendJSON($gameData);
    }

    public function sendMovingObjects(): void
    {
        $gameData = [
            'obj' => [],
            's' => $this->state,
        ];

        foreach ($this->gameState['player'] as $player) {
            $gameData['obj'][] = $player->toArray();
        }

        $this->sendJSON($gameData);
    }

    private function sendJSON(array $message): void
    {
        $encodedMessage = json_encode($message);
        foreach ($this->clients as $client) {
            $client->send($encodedMessage);
        }
    }

    private function generateLevel()
    {
        $this->gameState += $this->levelGenerator->generateInitialPack();
        $this->canGenerateLevel = false;
    }

    private function setStartPositions()
    {
        if (!array_key_exists('platform', $this->gameState)) {
            return;
        }

        $lowestPlatform = $this->gameState['platform'][0];
        foreach ($this->gameState['player'] as $player) {
            if ($player->getX() === 0.0) {
                $player->setPosition($lowestPlatform->getX(), $lowestPlatform->getTop() - $player->getTop() - 100);
            }
        }
    }

    private function disconnectPlayer(ConnectionInterface $player)
    {
        $this->clients->detach($player);
        unset($this->gameState['player'][$player->resourceId]);
        echo "Player {$player->resourceId} has disconnected!\n";
    }

    private function handleMessage(ConnectionInterface $from, $data)
    {
        try {
            $data = json_decode($data, true);
            isset($data['T'])
                ? $type = $data['T']
                : throw new \InvalidArgumentException("Wrong Client message. Missing message type.\n");
            switch ($type) {
                case JsonConstants::INITIALIZE_MESSAGE:
                    $this->onInit($data);
                    break;
                case JsonConstants::MOVE_MESSAGE:
                    $this->onMove($from, $data);
                    break;
                case JsonConstants::READY_MESSAGE:
                    $this->onReady($from, $data);
                    break;
                default:
                    throw new \InvalidArgumentException("Unexpected message type.\n");
            }
        } catch (\InvalidArgumentException $e) {
            echo $e->getMessage();
        }
    }

    private function onInit(array $data)
    {
        if ($this->canGenerateLevel) {
            $this->screen->width = $data['w'];
            $this->screen->height = $data['h'];
            $this->levelGenerator->updateScreen($this->screen);
            $this->collisionHandler->updateScreen($this->screen);
            $this->generateLevel();
            $this->setStartPositions();
            $this->sendGameState();
        }
    }

    private function onMove(ConnectionInterface $from, array $data)
    {
        if ($this->isRunning) {
            $playerId = $from->resourceId;
            if (isset($this->gameState['player'][$playerId])) {
                $this->gameState['player'][$playerId]->updatePositionX($data['x'], $this->deltaTime);
            }
        }
    }

    private function onReady(ConnectionInterface $from, array $data)
    {
        if ($data['r'] && !$this->canGenerateLevel) {
            $this->gameState['player'][$from->resourceId]->isReady = true;
            $this->isRunning = true;
            $this->state = self::RUNNING;
            $this->sendGameState();
        }
    }

    private function onGameEnd()
    {
        $this->state = self::END;
        $this->isRunning = false;

        $gameData = [
            'obj' => [],
            's' => $this->state,
        ];

        foreach ($this->gameState['player'] as $player) {
            $gameData['obj'][] = $player->toArray();
        }

        $this->sendJSON($gameData);
    }

    private function tryEndGame(): void
    {
        if (isset($this->gameState['endZone']) && $this->gameState['endZone'][0]->getIsActive() === true) {
            $this->onGameEnd();
        }
    }

    private function restart()
    {
        $this->canGenerateLevel = true;
        $this->isRunning = false;
        $this->state = self::LOBBY;
        $this->gameState = [
            'player' => [],
        ];
    }
}
