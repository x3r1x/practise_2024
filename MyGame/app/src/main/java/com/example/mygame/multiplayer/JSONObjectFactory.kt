package com.example.mygame.multiplayer

class JSONObjectFactory {
    fun getPlayerFromJSON(data: Array<Any>, score: Float) : PlayerJSON {
        val posX = (data[1] as Double).toFloat()
        val posY = (data[2] as Double).toFloat() + score

        val speedX = (data[3] as Double).toFloat()
        val speedY = (data[4] as Double).toFloat()

        val id = (data[5] as Double).toInt()

        val directionX = (data[6] as Double).toInt()
        val directionY = (data[7] as Double).toInt()

        val isWithShield = (data[8] as Double).toInt()
        val isShooting = (data[9] as Double).toInt()
        val isDead = (data[10] as Double).toInt()

        return PlayerJSON(posX, posY, speedX, speedY, directionX, directionY, isWithShield, isShooting, isDead, id)
    }

    fun getPlatformFromJSON(data: Array<Any>, score: Float) : PlatformJSON {
        val type = (data[0] as Double).toInt()

        val posX = (data[1] as Double).toFloat()
        val posY = (data[2] as Double).toFloat() + score

        val speedX = (data[3] as Double).toFloat()
        val speedY = (data[4] as Double).toFloat()

        val animationTime = (data[5] as Double).toInt()

        return PlatformJSON(posX, posY, speedX, speedY, type, animationTime)
    }

    fun getEnemyFromJSON(data: Array<Any>, score: Float) : EnemyJSON {
        val type = (data[0] as Double).toInt()

        val posX = (data[1] as Double).toFloat()
        val posY = (data[2] as Double).toFloat() + score

        val speedX = (data[3] as Double).toFloat()
        val speedY = (data[4] as Double).toFloat()

        return EnemyJSON(posX, posY, speedX, speedY, type)
    }

    fun getBonusFromJSON(data: Array<Any>, score: Float) : BonusJSON {
        val type = (data[0] as Double).toInt()

        val posX = (data[1] as Double).toFloat()
        val posY = (data[2] as Double).toFloat() + score

        val speedX = (data[3] as Double).toFloat()
        val speedY = (data[4] as Double).toFloat()

        val animationTime = (data[5] as Double).toInt()

        return BonusJSON(posX, posY, speedX, speedY, type, animationTime)
    }

    fun getBulletFromJSON(data: Array<Any>, score: Float) : BulletJSON {
        val posX = (data[1] as Double).toFloat()
        val posY = (data[2] as Double).toFloat() + score

        val speedX = (data[3] as Double).toFloat()
        val speedY = (data[4] as Double).toFloat()

        return BulletJSON(posX, posY, speedX, speedY)
    }
}