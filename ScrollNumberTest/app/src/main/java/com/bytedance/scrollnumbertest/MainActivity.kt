package com.bytedance.scrollnumbertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import com.bytedance.scrollnumbertest.view.MultiScrollNumber
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AccelerateDecelerateInterpolator()
        val num = 2

        val multiScrollNumber = findViewById<MultiScrollNumber>(R.id.multiScrollNumber)
        multiScrollNumber.initNum(numUpScroll(num, 8),true)

        button.setOnClickListener {
            multiScrollNumber.scrollTo(num.toString())
        }
    }


}

fun numUpScroll(num:Number, x:Int): String{
    val str = num.toString()
    val sb = StringBuilder()
    for(ch in str){
        if(ch != '.'){
            val n = Character.getNumericValue(ch)
            sb.append(
                if(n < x){
                    n + 10 - x
                }else{
                    n - x
                })
        }else{
            sb.append('.')
        }
    }
    return sb.toString()
}

fun main() {
    val num = 159
    print(numUpScroll(num, 2))
}