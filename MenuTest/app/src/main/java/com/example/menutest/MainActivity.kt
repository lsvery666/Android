package com.example.menutest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_item -> {
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()
                Log.d("Test", "add")
                val intent = Intent("com.example.menutest.TestActivity1")
                startActivity(intent)
                Log.d("Test", "after")
            }
            R.id.remove_item -> Toast.makeText(this, "Remove", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}