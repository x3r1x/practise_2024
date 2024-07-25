<?php

namespace App\Model\Platform;

use App\Model\Platform;
use App\Constants\JsonConstants;

class StaticPlatform extends Platform
{

    public function toArray(): array
    {
        return [
            JsonConstants::STATIC_PLATFORM_TYPE,
            round($this->x, 2),
            round($this->y, 2),
            $this->id,
            0,
        ];
    }
}
