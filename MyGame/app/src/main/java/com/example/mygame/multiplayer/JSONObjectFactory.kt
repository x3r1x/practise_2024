package com.example.mygame.multiplayer

class JSONObjectFactory {
    fun getPlayerFromJSON(data: Array<Double>, score: Float) : PlayerJSON {
        val posX = data[1].toFloat()
        val posY = data[2].toFloat() + score

        val prevX = posX
        val prevY = posY

        val speedX = data[3].toFloat()
        val speedY = data[4].toFloat()

        val id = data[5].toInt()

        val directionX = data[6].toInt()
        val directionY = data[7].toInt()

        val isWithShield = data[8].toInt()
        val isShooting = data[9].toInt()
        val isDead = data[10].toInt()

        return PlayerJSON(id, posX, posY, speedX, speedY, directionX, directionY, isWithShield, isShooting, isDead, prevX, prevY)
    }

    fun getPlatformFromJSON(data: Array<Double>, score: Float) : PlatformJSON {
        val type = data[0].toInt()

        val posX = data[1].toFloat()
        val posY = data[2].toFloat() + score

        val prevX = posX
        val prevY = posY

        val speedX = data[3].toFloat()
        val speedY = data[4].toFloat()

        val id = data[5].toInt()

        val animationTime = data[6].toInt()

        return PlatformJSON(id, posX, posY, speedX, speedY, type, animationTime, prevX, prevY)
    }

    fun getEnemyFromJSON(data: Array<Double>, score: Float) : EnemyJSON {
        val type = data[0].toInt()

        val posX = data[1].toFloat()
        val posY = data[2].toFloat() + score

        val prevX = posX
        val prevY = posY

        val speedX = data[3].toFloat()
        val speedY = data[4].toFloat()

        val id = data[5].toInt()

        return EnemyJSON(id, posX, posY, speedX, speedY, type, prevX, prevY)
    }

    fun getBonusFromJSON(data: Array<Double>, score: Float) : BonusJSON {
        val type = data[0].toInt()

        val posX = data[1].toFloat()
        val posY = data[2].toFloat() + score

        val prevX = posX
        val prevY = posY

        val speedX = data[3].toFloat()
        val speedY = data[4].toFloat()

        val id = data[5].toInt()

        val animationTime = data[6].toInt()

        return BonusJSON(id, posX, posY, speedX, speedY, type, animationTime, prevX, prevY)
    }

    fun getBulletFromJSON(data: Array<Double>, score: Float) : BulletJSON {
        val type = data[0].toInt()

        val posX = data[1].toFloat()
        val posY = data[2].toFloat() + score

        val prevX = posX
        val prevY = posY

        val speedX = data[3].toFloat()
        val speedY = data[4].toFloat()

        val id = data[5].toInt()

        return BulletJSON(id, posX, posY, speedX, speedY, prevX, prevY)
    }

    fun updateObject(prevObject: IGameObjectJSON, updatedObject: IGameObjectJSON) : IGameObjectJSON {
        val prevX = prevObject.x
        val prevY = prevObject.y

        updatedObject.prevX = prevX
        updatedObject.prevY = prevY

        return updatedObject
    }
}