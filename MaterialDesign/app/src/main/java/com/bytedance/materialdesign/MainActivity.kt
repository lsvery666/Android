package com.bytedance.materialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val fruits = mutableListOf<Fruit>(
        Fruit("apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango)
    )

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        navView.setCheckedItem(R.id.navCall)
        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            true
        }

        fab.setOnClickListener{
            Snackbar.make(it, "Data deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo"){
                    toast("Data restored")
                }
                .show()
        }

        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        recyclerview.layoutManager = layoutManager
        val adapter = FruitAdapter(this, fruitList)
        recyclerview.adapter = adapter

        swipeRefresh.setColorSchemeResources(R.color.design_default_color_primary)
        swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }
    }

    private fun initFruits(){
        fruitList.clear()
        repeat(50){
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    private fun refreshFruits(adapter: FruitAdapter){
        thread{
            Thread.sleep(1000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.backup -> toast("backup")
            R.id.delete -> toast("delete")
            R.id.settings -> toast("settings")
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    private fun toast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}