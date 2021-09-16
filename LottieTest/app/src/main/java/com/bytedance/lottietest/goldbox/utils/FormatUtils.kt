package com.bytedance.news.ug.luckycat.goldbox.utils

import java.lang.StringBuilder
import java.util.Random

/**
 * 处理各种格式化
 */
object FormatUtils {
    /**
     * 数字类型归零
     * 如: 1.1 -> 0.0
     * 如: 11 -> 00
     */
    fun num2zero(num: Number): String{
        val str = num.toString()
        val sb = StringBuilder()
        for(ch in str){
            if(ch != '.'){
                sb.append('0')
            }else{
                sb.append('.')
            }
        }
        return sb.toString()
    }

    /**
     * 数字类型随机化
     * 如: 1.1 -> *.*
     * 如: 11 -> **
     */
    fun num2random(num:Number):String{
        val random = Random()
        val str = num.toString()
        val sb = StringBuilder()
        for(ch in str){
            if(ch != '.'){
                sb.append(random.nextInt(10))
            }else{
                sb.append('.')
            }
        }
        return sb.toString()
    }

    /**
     * 数字类型各位向上滚动x
     * 如：123向上滚动2:901
     *    0.32向上滚动1:9.21
     */
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

    /**
     * 毫秒转换为xx时xx分xx秒
     * @param onlyShowValid: 只展示有效数字，如01分00秒，展示为1分钟，00分45秒展示为45秒
     */
    fun mills2str(mills: Long?, onlyShowValid: Boolean=false): String? {

        if (mills == null || mills < 1000) {
            return null
        }

        val hour = (mills / (1000 * 60 * 60)).toInt()
        val minute = (mills / (1000 * 60)).toInt() % 60
        val second = (mills - hour * (1000 * 60 * 60) - minute * (1000 * 60)).toInt() / 1000

        val sb = StringBuilder()
        sb.apply {
            if(onlyShowValid){
                append(if (hour == 0) ""  else "${hour}时")
                append(if (minute == 0) "" else "${minute}分")
                append(if (second == 0 && minute!=0) "钟" else if(second == 0 && minute==0) "" else if(second < 10) "0${second}秒" else "${second}秒")
            }else{
                append(if(hour == 0) "" else if(hour < 10) "0${hour}时" else "${hour}时")
                append(if(minute < 10) "0${minute}分" else "${minute}分")
                append(if(second < 10) "0${second}秒" else "${second}秒")
            }
        }

        return sb.toString()
    }

}

