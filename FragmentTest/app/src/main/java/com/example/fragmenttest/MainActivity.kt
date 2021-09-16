package com.example.fragmenttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var fragmentArray = SparseArray<Myfragment>(3)

    companion object{
        const val FRAGMENT1 = 1
        const val FRAGMENT2 = 2
        const val FRAGMENT3 = 3
    }

//    init {
//        val myfragment1 = Myfragment("Fragment1")
//        val myfragment2 = Myfragment("Fragment2")
//        val myfragment3 = Myfragment("Fragment3", true)
//
//        fragmentArray.put(FRAGMENT1, myfragment1)
//        fragmentArray.put(FRAGMENT2, myfragment2)
//        fragmentArray.put(FRAGMENT3, myfragment3)
//    }

    var selectedIndex = FRAGMENT1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 默认Fragment
        performTabChanged(selectedIndex)
        radioGroup.check(R.id.button1)

        button1.setOnClickListener{ performTabChanged(FRAGMENT1) }
        button2.setOnClickListener{ performTabChanged(FRAGMENT2) }
        button3.setOnClickListener{ performTabChanged(FRAGMENT3) }

    }

    fun performTabChanged(checkedIndex: Int){
        val transaction = supportFragmentManager.beginTransaction()

        // 使用replace方法  实际是remove + add
//        var targetFragment = fragmentArray.get(checkedIndex)
//        if(targetFragment == null){
//            targetFragment = obtainFragment(checkedIndex)
//        }
//        transaction.replace(R.id.layout_fragment, targetFragment, "Fragment" + checkedIndex)
//        transaction.addToBackStack(null)


//         使用hide add show方法
        val prevFragment = fragmentArray.get(selectedIndex)
        var targetFragment = fragmentArray.get(checkedIndex)
        if(prevFragment != null) {
            transaction.hide(prevFragment)
        }
        if(targetFragment == null) {
            targetFragment = obtainFragment(checkedIndex)
            // 同一个fragment只能添加一次 Fragment already added
            transaction.add(R.id.layout_fragment, targetFragment, "Fragment$checkedIndex")
        }
        transaction.show(targetFragment)

        transaction.commitAllowingStateLoss()
        selectedIndex = checkedIndex
    }

    fun obtainFragment(checkedIndex: Int): Myfragment{
        val fragment = when(checkedIndex){
            FRAGMENT1 -> Myfragment("Fragment1")
            FRAGMENT2 -> Myfragment("Fragment2")
            FRAGMENT3 -> Myfragment("Fragment3", true)
            else -> null
        }
        fragmentArray.put(checkedIndex, fragment)
        return fragment!!
    }
}