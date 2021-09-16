package com.ss.android.article.jetpacktest

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel:MainViewModel
    lateinit var sp: SharedPreferences
    val observer = MyObserver(lifecycle)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(observer)

        sp = getPreferences(0)
        val countReserved = sp.getInt("count_reserved", 0)

        // 不带参数的ViewModel
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // 带参数的ViewModel
        viewModel = ViewModelProviders.of(this, MainViewModelFactory(countReserved)).get(MainViewModel::class.java)
        plusOneButton.setOnClickListener {
            viewModel.plusOne()
        }
        clearBtn.setOnClickListener {
            viewModel.clear()
        }

        // 当viewModel.counter的数据发生改变时调用
        viewModel.counter.observe(this, Observer {
            infoText.text = it.toString()
        })

        getUserBtn.setOnClickListener {
            val userId = (0..10000).random().toString()
            viewModel.getUser(userId)
        }

        viewModel.user.observe(this, Observer {
            userName.text = "${it.firstName} ${it.lastName} ${it.Id}"
        })

    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved", viewModel.counter.value?: 0)
        }
    }

}