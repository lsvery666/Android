package com.bytedance.productflavorstest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, getRepo().toString(), Toast.LENGTH_SHORT).show()
    }

    fun getRepo(): Repo{
        return Repo.javaClass.newInstance()
//        return Class.forName("com.bytedance.productflavorstest2.alpha.Repo").newInstance() as Repo
    }
}