package com.example.mygame.logic

import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.platforms.DisappearingPlatform

class ObjectsHandler {
    fun updateObjects(screen: Screen, objects: List<IDrawable>) : List<IDrawable> {
        return objects.filterNot {
            it is ICollidable && it !is Player && it.collidesWith(screen) == true || (it is DisappearingPlatform && it.isDestroying)
        }
    }
}