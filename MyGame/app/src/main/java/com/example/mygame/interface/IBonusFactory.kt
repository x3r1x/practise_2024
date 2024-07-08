package com.example.mygame.`interface`

import com.example.mygame.`object`.platform.StaticPlatform

interface IBonusFactory {
    fun generateBonus(staticPlatform: StaticPlatform)
}