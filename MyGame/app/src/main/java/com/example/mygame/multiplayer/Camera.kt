package com.example.mygame.multiplayer

import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.Score
import com.example.mygame.domain.Screen
import com.example.mygame.domain.drawable.factory.PlayerViewFactory

class Camera(
    private val screen: Screen,
    private val score: Score,
    private val objects: MutableList<IGameObjectJSON>
) {
    private val scrollingDownLine = screen.height - GameConstants.SCREEN_SCROLLING_DOWN_LINE
    private val scrollingUpLine = screen.height / 2
    private var lastPlayerY: Float = 0f // Хранит последнюю позицию игрока по Y

    fun updatePositions(player: PlayerJSON) {
        // Проверяем, нужно ли сместить экран вверх
        if (isNeedScrollUp(player.y)) {
            val offsetY = getUpOffsetY(player.y)
            score.increase(offsetY)
            screenScroll(objects, offsetY)
        }
        // Проверяем, нужно ли сместить экран вниз
        /*else if (isNeedScrollDown(player)) {
            val offsetY = getDownOffsetY(player.y)
            screenScroll(objects, -offsetY) // Смещение вниз
            score.increase(-offsetY) // Предполагаем, что нужно уменьшить счет при смещении вниз
        }*/

        // Обновляем последнюю позицию игрока
        lastPlayerY = player.y
    }

    private fun screenScroll(elements: List<IGameObjectJSON>, offsetY: Float) {
        elements.forEach {
            it.y += offsetY
            it.prevY += offsetY
        }
    }

    private fun isNeedScrollDown(player: PlayerJSON): Boolean {
        return player.directionY == PlayerViewFactory.DIRECTION_Y_DOWN && player.y >= scrollingDownLine && player.y > lastPlayerY
    }

    private fun isNeedScrollUp(playerY: Float): Boolean {
        return playerY < scrollingUpLine && playerY < lastPlayerY
    }

    private fun getDownOffsetY(playerY: Float): Float {
        return playerY - scrollingDownLine
    }

    private fun getUpOffsetY(playerY: Float): Float {
        return scrollingUpLine - playerY // Изменено, чтобы корректно возвращать смещение вверх
    }
}
