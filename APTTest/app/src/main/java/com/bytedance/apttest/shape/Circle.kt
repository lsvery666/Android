package com.bytedance.apttest.shape

import com.bytedance.annotation.Factory

@Factory(type = IShape::class, id = "Circle")
class Circle: IShape {
    override fun draw() {
        println("Draw a circle.")
    }
}