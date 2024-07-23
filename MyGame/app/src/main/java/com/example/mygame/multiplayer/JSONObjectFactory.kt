package com.example.mygame.multiplayer

class JSONObjectFactory {
    fun getPlayerFromJSON(data: Array<Any>, score: Float) : PlayerJSON {
        val posX = (data[1] as Double).toFloat()
        val posY = (data[2] as Double).toFloat() + score

        val prevX = posX
        val prevY = posY

        val speedX = (data[3] as Double).toFloat()
        val speedY = (data[4] as Double).toFloat()

        val id = (data[5] as Double).toInt()

        val directionX = (data[6] as Double).toInt()
        val directionY = (data[7] as Double).toInt()

        val isWithShield = (data[8] as Double).toInt()
        val isShooting = (data[9] as Double).toInt()
        val isDead = (data[10] as Double).toInt()

        return PlayerJSON(id, posX, posY, speedX, speedY, directionX, directionY, isWithShield, isShooting, isDead, prevX, prevY)
    }

    fun getPlatformFromJSON(data: Array<Any>, score: Float) : PlatformJSON {
        val type = (data[0] as Double).toInt()

        val posX = (data[1] as Double).toFloat()
        val posY = (data[2] as Double).toFloat() + score

        val prevX = posX
        val prevY = posY

        val speedX = (data[3] as Double).toFloat()
        val speedY = (data[4] as Double).toFloat()

        val id = (data[5] as Double).toInt()

        val animationTime = (data[6] as Double).toInt()

        return PlatformJSON(id, posX, posY, speedX, speedY, type, animationTime, prevX, prevY)
    }

    fun getEnemyFromJSON(data: Array<Any>, score: Float) : EnemyJSON {
        val type = (data[0] as Double).toInt()

        val posX = (data[1] as Double).toFloat()
        val posY = (data[2] as Double).toFloat() + score

        val prevX = posX
        val prevY = posY

        val speedX = (data[3] as Double).toFloat()
        val speedY = (data[4] as Double).toFloat()

        val id = (data[5] as Double).toInt()

        return EnemyJSON(id, posX, posY, speedX, speedY, type, prevX, prevY)
    }

    fun getBonusFromJSON(data: Array<Any>, score: Float) : BonusJSON {
        val type = (data[0] as Double).toInt()

        val posX = (data[1] as Double).toFloat()
        val posY = (data[2] as Double).toFloat() + score

        val prevX = posX
        val prevY = posY

        val speedX = (data[3] as Double).toFloat()
        val speedY = (data[4] as Double).toFloat()

        val id = (data[5] as Double).toInt()

        val animationTime = (data[6] as Double).toInt()

        return BonusJSON(id, posX, posY, speedX, speedY, type, animationTime, prevX, prevY)
    }

    fun getBulletFromJSON(data: Array<Any>, score: Float) : BulletJSON {
        val posX = (data[1] as Double).toFloat()
        val posY = (data[2] as Double).toFloat() + score

        val prevX = posX
        val prevY = posY

        val speedX = (data[3] as Double).toFloat()
        val speedY = (data[4] as Double).toFloat()

        val id = (data[5] as Double).toInt()

        return BulletJSON(id, posX, posY, speedX, speedY, prevX, prevY)
    }

    fun updateObject(prevObject: IGameObjectJSON, updatedObject: IGameObjectJSON) : IGameObjectJSON {
        val prevX = prevObject.prevX
        val prevY = prevObject.prevY

        updatedObject.prevX = prevX
        updatedObject.prevY = prevY

        return updatedObject
    }
}