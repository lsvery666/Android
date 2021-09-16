package com.bytedance.lottietest

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import com.bytedance.lottietest.goldbox.callback.BoxInfoCallback
import com.bytedance.lottietest.goldbox.listener.BoxOpenListener
import com.bytedance.lottietest.goldbox.model.OpenableInfo
import com.bytedance.lottietest.goldbox.model.RewardType
import com.bytedance.lottietest.goldbox.model.TimerInfo
import com.bytedance.lottietest.goldbox.model.UnOpenableInfo
import com.bytedance.lottietest.goldbox.timer.BoxTimer
import com.bytedance.lottietest.goldbox.view.GoldCoinBox
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url =
            "https://sf1-hscdn-tos.pstatp.com/obj/toutiao-cdn/gold_coin_box_shake_and_open.zip"
        LottieCompositionFactory.fromUrl(this, url)

        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.play)
        val lottie = findViewById<LottieAnimationView>(R.id.lottie_view)
        val box = findViewById<GoldCoinBox>(R.id.gold_box)
        BoxTimer.startTiming(TimerInfo(1000, 0, 0))
        BoxTimer.timeTextLd.observe(this, box.countDownObserver)
        box.boxOpenListener = object : BoxOpenListener{
            override fun onOpenable(boxInfoCallback: BoxInfoCallback<OpenableInfo>) {
                boxInfoCallback.onSuccess(OpenableInfo(RewardType.COIN, 10, 1000))
            }

            override fun onUnOpenable(boxInfoCallback: BoxInfoCallback<UnOpenableInfo>) {
                boxInfoCallback.onSuccess(UnOpenableInfo(RewardType.COIN, 10))
            }

        }

        lottie.addAnimatorListener(
            object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    Log.d(TAG, "onAnimationStart")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Log.d(TAG, "onAnimationEnd")
                    button.isClickable = true
                }

                override fun onAnimationCancel(animation: Animator?) {
                    Log.d(TAG, "onAnimationCancel")
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    Log.d(TAG, "onAnimationRepeat")
                }

            }
        )

        lottie.addAnimatorUpdateListener {
            Log.d(TAG, it.animatedValue.toString())
        }

        lottie.addLottieOnCompositionLoadedListener {
            Log.d(TAG, "onCompositionLoaded")
        }

        button.setOnClickListener {
            button.isClickable = false
            val countDownTimer = object : CountDownTimer(5000, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    textView.text = millisUntilFinished.toString()
                }

                override fun onFinish() {
                }

            }.start()

            lottie.setAnimationFromUrl(url)
            lottie.setMinAndMaxFrame(276, 372)
            lottie.playAnimation()
        }

    }
}