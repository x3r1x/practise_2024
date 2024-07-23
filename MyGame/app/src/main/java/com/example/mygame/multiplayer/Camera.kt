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
    private val scrollingUpLine = screen.height / 2

    fun updatePositions(player: PlayerJSON, elements: List<IGameObjectJSON>) {
        if (isNeedScrollUp(player.y)) {
            val offsetY = getUpOffsetY(player.y)
//            /screenScroll(elements, sc)
            //score.increase(offsetY)
        } //else if (isNeedScrollDown(player)) {
          //  val offsetY = getDownOffsetY(player.y)
          //  screenScroll(elements, offsetY)
        //}
    }

    private fun screenScroll(elements: List<IGameObjectJSON>, offsetY: Float) {
        elements.forEach {
            it.y += score.getScore()
        }
    }

    private fun isNeedScrollDown(player: PlayerJSON) : Boolean {
        return player.directionY == PlayerViewFactory.DIRECTION_Y_DOWN && player.y >= scrollingDownLine
    }

    private fun isNeedScrollUp(playerY: Float) : Boolean {
        return playerY < scrollingUpLine
    }

    private fun getDownOffsetY(playerY: Float) : Float {
        return playerY - scrollingDownLine
    }

    private fun getUpOffsetY(playerY: Float) : Float {
        return playerY - scrollingUpLine
    }
}