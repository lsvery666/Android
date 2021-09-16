package com.example.viewpagertest2

import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var bmpw = 0        // 游标宽度
    private var offset = 0.0f      // 动画图片偏移量
    private var currIndex = 0   // 当前页卡编号
    lateinit var viewList: List<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 准备数据 page
        val inflate = {layout: Int -> layoutInflater.inflate(layout, null)}
        viewList = listOf<View>(inflate(R.layout.layout1), inflate(R.layout.layout2), inflate(R.layout.layout3))

        initCursorPos()

        viewPager.adapter = object : PagerAdapter(){
            override fun getCount(): Int {
                return viewList.size
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                container.addView(viewList[position])
                return viewList[position]
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(viewList[position])
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                val one = offset * 2 + bmpw
                val two = one * 2

                val animation = when(position){
                    0 -> if(currIndex == 1){TranslateAnimation(one, 0f, 0f, 0f)}else{TranslateAnimation(two, 0f, 0f, 0f)}
                    1 -> if(currIndex == 0){TranslateAnimation(offset, one, 0f, 0f)}else{TranslateAnimation(two, one, 0f, 0f)}
                    2 -> if(currIndex == 0){TranslateAnimation(offset, two, 0f, 0f)}else{TranslateAnimation(one, two, 0f, 0f)}
                    else -> null
                }

                currIndex = position
                animation?.fillAfter = true
                animation?.duration = 300
                cursor.startAnimation(animation)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    private fun initCursorPos(){
        bmpw = BitmapFactory.decodeResource(resources, R.drawable.ic_cursor).width
        val pageWidth = resources.getDimensionPixelSize(R.dimen.viewPager_width)
        offset = (pageWidth / viewList.size - bmpw) / 2.0f
        Log.d("MainActivity", "$bmpw : $pageWidth : $offset")
        val matrix = Matrix()
        matrix.postTranslate(200.0f, 0f)
        cursor.imageMatrix = matrix
    }
}