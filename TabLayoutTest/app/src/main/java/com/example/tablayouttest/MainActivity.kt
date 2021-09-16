package com.example.tablayouttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragments = listOf<Fragment>(Fragment1(), Fragment2(), Fragment3())
        val titles = listOf<String>("fragment1", "fragment2", "fragment3")

        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getCount(): Int = fragments.size

            override fun getItem(position: Int): Fragment = fragments[position]

            override fun getPageTitle(position: Int): CharSequence? = titles[position]
        }

        tabLayout.setupWithViewPager(viewPager)

    }
}