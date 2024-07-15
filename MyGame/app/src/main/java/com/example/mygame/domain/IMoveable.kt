package com.example.mygame.domain

interface IMoveable {
    val x: Float
    val y: Float

    fun setPosition(startX: Float, startY: Float) {}
    fun updatePosition(elapsedTime: Float) {}
}