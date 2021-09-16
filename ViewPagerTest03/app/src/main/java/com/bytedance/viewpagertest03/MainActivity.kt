package com.bytedance.viewpagertest03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment1 = MyFragment(1)
        val fragment2 = MyFragment(2)
        val fragment3 = MyFragment(3)
        val fragment4 = MyFragment(4)
        val fragment5 = MyFragment(5)

        val fragments = listOf<MyFragment>(fragment1, fragment2, fragment3, fragment4, fragment5)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Log.d(TAG, "onScrolled")
            }
        })
    }
}