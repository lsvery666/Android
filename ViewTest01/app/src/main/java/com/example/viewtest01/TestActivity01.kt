package com.example.viewtest01

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.widget.Scroller
import android.widget.Toast
import com.example.viewtest01.view.MyDialog
import kotlinx.android.synthetic.main.activity_test01.*

class TestActivity01 : Activity(){

    private var mCount = 0


    companion object {
        private const val TAG = "TestActivity01"
        private const val MESSAGE_SCROLL_TO = 1
        private const val FRAME_COUNT = 30
        private const val DELAYED_TIME = 33
    }

    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_SCROLL_TO -> {
                    mCount++
                    if (mCount <= FRAME_COUNT) {
                        val fraction = mCount / FRAME_COUNT.toFloat()
                        val scrollX = (fraction * 100).toInt()
                        button1!!.scrollTo(scrollX, 0)
                        sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME.toLong())
                    }
                }
                else -> {
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test01)

        button1.setOnClickListener{
            Log.d(TAG, "button1 is clicked.")

            // scrollTo()方法
            mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME.toLong())


            // 属性动画
//            ObjectAnimator.ofFloat(button1, "translationX", 0f, 100f)
//                .setDuration(1000).start()

            // scrollTo() + 动画
//            val startX = 0
//            val deltaX = 100
//            val animator: ValueAnimator = ValueAnimator.ofInt(0, 1).setDuration(1000)
//            animator.addUpdateListener {
//                button1.scrollTo(startX + (deltaX * it.animatedFraction).toInt(), 0)
//            }
//            animator.start()

            // 改变布局参数
//            val params = button1.layoutParams as LinearLayout.LayoutParams
//            Log.d(TAG, "button1.width: " + button1.width)
//            Log.d(TAG, "params.width: ${params.width}")
//            Log.d(TAG, "leftMargin: ${params.leftMargin}")
//            // params.width += 100  // 如果你设置了"match_parent","wrap_content",返回的值是-1和-2,其实是定义的常量:
//            params.leftMargin += 100
//            button1!!.layoutParams = params

        }

        button1.setOnLongClickListener{
            Toast.makeText(this, "long click", Toast.LENGTH_SHORT).show()
            true
        }

        button1.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> Log.d(TAG, "button1 action down")
                MotionEvent.ACTION_MOVE -> Log.d(TAG, "button1 action move")
                MotionEvent.ACTION_UP -> Log.d(TAG, "button1 action up")
            }
            false
        }

        button3.setOnClickListener {
            val a = intArrayOf(0, 0)
            button2.getLocationInWindow(a)
            Log.d(TAG, ""+a[0]+" "+a[1])
            Log.d(TAG, ""+button2.x+""+button2.y)
            MyDialog(this, a).show()
        }

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        Log.d(TAG, "button1.left=" + button2.left)
        Log.d(TAG, "button1.x=" + button2.x)

        // translation属性
        button2.translationX = 100f
        Log.d(TAG, "button1.left=" + button2.left)
        Log.d(TAG, "button1.x=" + button2.x)

    }

}
