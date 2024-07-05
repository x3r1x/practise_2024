package com.example.mygame.`object`.iteractable

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.mygame.R
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen

class Jetpack(resources: Resources, entity: Player) : IDrawable, ICollidable {
    private val bitmap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTIONS)

    override var x = 0f
    override var y = 0f

    override var left = 0f
    override var top = 0f
    override var bottom = 0f
    override var right = 0f

    override val isPassable = true
    override var isInSpring: Boolean? = null

    fun createOnPlatform(platform: Platform) {
        setPosition(platform.x, platform.top - HEIGHT / 2 - OFFSET)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, left, top, null)
    }

    override fun setPosition(newX: Float, newY: Float) {
        x = newX
        y = newY
        left = x - WIDTH / 2
        right = x + WIDTH / 2
        top = y - HEIGHT / 2
        bottom = y + HEIGHT / 2
    }

    override fun updatePositionX(newX: Float) {}
    override fun updatePositionY(elapsedTime: Float) {}

    override fun onScreenCollide(screen: Screen) {}

    override fun onObjectCollide(obj: ICollidable) {
        TODO("Not yet implemented")
    }

    override fun collidesWith(other: ICollidable?): Boolean? {
        TODO("Not yet implemented")
    }

    companion object {
        private const val WIDTH = 124f
        private const val HEIGHT = 111f
        private const val OFFSET = 20f

        private val IMAGE = R.drawable.jetpack

        private val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}