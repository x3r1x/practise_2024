package com.example.mygame.multiplayer

import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.Score
import com.example.mygame.domain.Screen
import com.example.mygame.domain.drawable.factory.PlayerViewFactory

class Camera(
    screen: Screen,
    private val score: Score
) {
    private val scrollingDownLine = screen.height - GameConstants.SCREEN_SCROLLING_DOWN_LINE
    private val scrollingUpLine = screen.height - GameConstants.MOVE_OBJECTS_LINE

    fun screenScroll(elements: List<IGameObjectJSON>, offsetY: Float) {
        score.increase(offsetY)
        elements.forEach {
            it.y += score.getScore()
        }
    }

    fun isNeedScrollDown(player: PlayerJSON) : Boolean {
        return player.directionY == PlayerViewFactory.DIRECTION_Y_DOWN && player.y >= scrollingDownLine
    }

    fun isNeedScrollUp(playerY: Float) : Boolean {
        return playerY < scrollingUpLine
    }

    fun getDownOffsetY(playerY: Float) : Float {
        return playerY - scrollingDownLine
    }

    fun getUpOffsetY(playerY: Float) : Float {
        return playerY - scrollingUpLine
    }
}