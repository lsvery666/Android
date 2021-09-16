package com.bytedance.valueanimationtest

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.animation.doOnEnd

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button

    val Float.dp: Float
        get() = (resources.displayMetrics.density * this) + 0.5f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1 = findViewById<Button>(R.id.button1)
        button2 = findViewById<Button>(R.id.button2)
        button3 = findViewById<Button>(R.id.button3)
        button4 = findViewById<Button>(R.id.button4)



        button1.setOnClickListener {
            // ViewPropertyAnimator
            button1.animate()
                .translationXBy(50f.dp)
                .translationYBy(50f.dp)
                .setDuration(2 * 1000)
                .start()
        }

        button2.setOnClickListener {
            // ObjectAnimator
            val animator1 = ObjectAnimator.ofFloat(button2, "translationX", 20f.dp, 50f.dp)
            animator1.duration = 2 * 1000

            val animator2 = ObjectAnimator.ofFloat(button2, "translationY", 50f.dp)
            animator2.duration = 2 * 1000

            val animator3 = ObjectAnimator.ofFloat(button2, "scaleX", 0.5f, 1f)
            animator3.duration = 2 * 1000

            animator1.doOnEnd {
                animator2.start()
            }

            animator2.doOnEnd {
                animator3.start()
            }

            animator3.startDelay = 2 * 1000  // start()之后开始计时

            animator1.start()
        }

        button3.setOnClickListener {
            // ValueAnimator
            val animator = ValueAnimator.ofFloat(0f.dp, 50f.dp)
            animator.duration = 2 * 1000
            animator.addUpdateListener {
                Log.d(TAG, "valueAnimator.animatedValue=" + animator.animatedValue)
                button3.translationX = animator.animatedValue as Float
            }
            animator.start()
        }

        button4.setOnClickListener {
            val animator1 = ObjectAnimator.ofFloat(button1, "translationX", 50f.dp)
            val animator2 = ObjectAnimator.ofFloat(button2, "translationX", 50f.dp)
            val animator3 = ObjectAnimator.ofFloat(button3, "translationX", 50f.dp)
            val animatorSet = AnimatorSet()
            animatorSet.play(animator1)
                .with(animator2)
                .after(animator3)
            animatorSet.duration = 2 * 1000
            animatorSet.start()
        }


    }
}