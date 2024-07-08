package com.example.mygame.`interface`

import com.example.mygame.`object`.platforms.StaticPlatform

interface IBonusFactory {
    fun generateBonus(staticPlatform: StaticPlatform)
}