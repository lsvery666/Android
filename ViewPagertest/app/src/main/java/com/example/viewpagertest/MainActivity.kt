package com.example.viewpagertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 准备数据
        val view1 = layoutInflater.inflate(R.layout.layout1, null)
        val view2 = layoutInflater.inflate(R.layout.layout2, null)
        val view3 = layoutInflater.inflate(R.layout.layout3, null)
        val viewList = listOf<View>(view1, view2, view3)

        // 标题 两种控件：PagerTabStrip和PagerTitleStrip
        // 其实这两个实现的效果基本差不多，但有两点不同：
        //1、PagerTabStrip在当前页面下，会有一个下划线条来提示当前页面的Tab是哪个。
        //2、PagerTabStrip的Tab是可以点击的，当用户点击某一个Tab时，当前页面就会跳转到这个页面，而PagerTitleStrip则没这个功能。
        // 这两者其实都不适合实际用途，因为这个标题是滑动的
        val titleList = listOf<String>("title1", "title2", "title3")

        viewPager.adapter = object : PagerAdapter() {

            // 四个必须重写的函数
            override fun getCount(): Int = viewList.size

            // 这里的`object`就是instantiateItem的返回值，也就是key
            // 用来判断instantiateItem函数所返回来的Key与一个页面视图是否是代表的同一个视图
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
//                return view == `object`
                return view == viewList[`object` as Int]
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                container.addView(viewList[position])
                // 返回值就是key，不一定非得是View，只要能和view一一对应即可
//                return viewList[position]
                return position
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(viewList[position])
            }

            // 获取标题必须要重写的函数
            override fun getPageTitle(position: Int): CharSequence? {
                return titleList[position]
            }
        }

    }


}