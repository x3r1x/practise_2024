package com.example.mygame.visitor

import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Player.DirectionX
import com.example.mygame.`object`.Player.DirectionY
import com.example.mygame.`object`.platforms.BreakingPlatform
import com.example.mygame.`object`.platforms.DisappearingPlatform

class PlayerCollisionVisitor(
    private val player: Player,
    private val screenHeight: Float
) : IVisitor {
    private fun IsCollidesPlayerWithPlatform(platform: Platform) : Boolean {
        if (player.getDirectionX() == DirectionX.RIGHT) {
            return player.bottom < platform.bottom && player.bottom >= platform.top && player.getDirectionY() == DirectionY.DOWN
                    && (player.left + 15f < platform.right && player.right - 50f > platform.left)
        } else {
            return player.bottom < platform.bottom && player.bottom >= platform.top && player.getDirectionY() == DirectionY.DOWN
                    && (player.left + 50f < platform.right && player.right - 15f > platform.left)
        }
    }

    override fun visit(platform: Platform) {
        if (IsCollidesPlayerWithPlatform(platform)) {
            if (platform is BreakingPlatform) {
                platform.runDestructionAnimation(screenHeight)
                return
            } else if (platform is DisappearingPlatform) {
                platform.animatePlatformColor()
            }

            player.repulsion()
        }
    }

    override fun visit(player: Player) {}
}