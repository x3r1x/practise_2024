<?php

namespace App\Command;

use App\Logic\Timer;
use React\EventLoop\Loop;
use App\Server\GameServer;
use Ratchet\Http\HttpServer;
use Ratchet\Server\IoServer;
use React\Socket\SocketServer;
use Ratchet\WebSocket\WsServer;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Attribute\AsCommand;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;

#[AsCommand(name: 'server:run')]
class RunServerCommand extends Command
{
    protected function configure()
    {
        $this->setDescription('Запустить Сервер');
    }

    protected function execute(InputInterface $input, OutputInterface $output): int
    {
        $loop = Loop::get();

        $gameServer = new GameServer();

        $interval = 1 / 60;
        $timer = new Timer($interval);

        $url = "10.250.104.162:8080";
        $webSocket = new SocketServer($url, [], $loop);
        $webServer = new IoServer(new HttpServer(new WsServer($gameServer)), $webSocket, $loop);

        $gameServer->testScene();

        $loop->addPeriodicTimer($interval, function () use ($gameServer, $timer) {
            if ($timer->checkAndUpdate()) {
                $deltaTime = microtime(true) - $gameServer->lastUpdateTime;
                $gameServer->updateGameState($deltaTime);
                $gameServer->lastUpdateTime = microtime(true);
            }
        });

        $loop->addPeriodicTimer(1.0, function () use ($gameServer) {
            $gameServer->sendData();
        });

        $output->writeln("WebSocket server started: $url");

        $loop->run();

        return Command::SUCCESS;
    }
}
