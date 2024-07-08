package com.example.mygame.`interface`

interface IGameObject {
    var isDisappeared: Boolean

    val left: Float
    val right: Float
    val top: Float
    val bottom: Float

    fun accept(visitor: IVisitor)
}