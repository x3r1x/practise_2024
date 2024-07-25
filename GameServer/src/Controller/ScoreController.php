<?php

namespace App\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

class ScoreController extends AbstractController
{
    private const MAX_NAME_LENGTH = 15;

    public function save(Request $request): Response
    {
        $jsonAsArray = json_decode($request->getContent(), true);

        if (!$this->validate($jsonAsArray)) {
            return new Response("don\'t use postman to increase your score!", 403, []);
        }

        $this->store($jsonAsArray);

        return new JsonResponse('', 200, [], true);
    }

    public function show(): Response
    {
        $jsonData = file_get_contents('data.json');

        return new JsonResponse($jsonData, 200, [], true);
    }

    private function validate(array &$message): bool
    {
        if (isset($message['name'], $message['score'])) {
            if (strlen($message['name']) > self::MAX_NAME_LENGTH) {
                echo $message['name'] . "\n";
                $message['name'] = substr($message['name'], 0, self::MAX_NAME_LENGTH);
                echo $message['name'];
            }
            return true;
        }
        return false;
    }

    private function store(array $newData): void
    {
        $jsonData = file_get_contents('data.json');
        $data = json_decode($jsonData, true);

        $data['leaders'][] = $newData;

        $this->sort($data['leaders']);

        if (sizeof($data['leaders']) > 7) {
            array_splice($data['leaders'], 7);
        }

        $this->saveToFile($data);
    }

    private function sort(array &$data): void
    {
        usort($data, function ($a, $b) {
            return $b['score'] <=> $a['score'];
        });
    }

    private function saveToFile(array $data)
    {
        $json = json_encode($data);
        $file = fopen('data.json', 'w+');
        fwrite($file, $json);
        fclose($file);
    }
}
