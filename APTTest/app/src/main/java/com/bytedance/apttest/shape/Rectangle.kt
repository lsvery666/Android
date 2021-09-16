package com.bytedance.apttest.shape

import com.bytedance.annotation.Factory

@Factory(type = IShape::class, id = "Rectangle")
class Rectangle : IShape {
    override fun draw() {
        println("Draw a rectangle.")
    }
}