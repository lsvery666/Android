package com.bytedance.radiobuttontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.marginStart

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup1 = findViewById<RadioGroup>(R.id.radioGroup1)
        val radioGroup2 = findViewById<RadioGroup>(R.id.radioGroup2)

        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            Log.d("Test", "radioGroup1 $checkedId")
            Log.d("Test", "radioGroup1.checkedRadioButtonId ${radioGroup1.checkedRadioButtonId}")
            Log.d("Test", "radioGroup2.checkedRadioButtonId ${radioGroup2.checkedRadioButtonId}")

            if(radioGroup2.checkedRadioButtonId != -1){
                radioGroup2.clearCheck()
            }
        }

        radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            Log.d("Test", "radioGroup2 $checkedId")
            Log.d("Test", "radioGroup1.checkedRadioButtonId ${radioGroup1.checkedRadioButtonId}")
            Log.d("Test", "radioGroup2.checkedRadioButtonId ${radioGroup2.checkedRadioButtonId}")

            if(radioGroup1.checkedRadioButtonId != -1){
                radioGroup1.clearCheck()
            }
        }
    }
}