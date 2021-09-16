package com.example.filepersistencetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    val fileName = "Test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val content = load(fileName)
        editText.setText(content)
        editText.setSelection(content.length)
    }

    override fun onDestroy() {
        super.onDestroy()
        save(fileName, editText.text.toString(), MODE_PRIVATE)
    }

    fun load(fileName: String): String {
        val stringBuilder = StringBuilder()
        try {
            val fileInputStream = openFileInput(fileName)
            val reader = BufferedReader(InputStreamReader(fileInputStream))
            reader.use {
                reader.forEachLine {
                    stringBuilder.append(it)
                    stringBuilder.append('\n')
                }
            }
            Toast.makeText(this,"restore", Toast.LENGTH_LONG).show()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

    fun save(fileName:String, content:String, mode:Int){
        try {
            val fileOutputStream = openFileOutput(fileName, mode)
            val writer = BufferedWriter(OutputStreamWriter(fileOutputStream))
            writer.use {
                writer.write(content)
            }
            Toast.makeText(this, "save", Toast.LENGTH_LONG).show()
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
}