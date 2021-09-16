package com.bytedance.lottietest.goldbox.view

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import com.bytedance.lottietest.R
import com.bytedance.lottietest.goldbox.callback.BoxInfoCallback
import com.bytedance.lottietest.goldbox.callback.ICoinIncomeDialogCallback
import com.bytedance.lottietest.goldbox.listener.BoxOpenListener
import com.bytedance.lottietest.goldbox.utils.Utils.getScreenRealHeight
import com.bytedance.lottietest.goldbox.utils.dpInt
import com.bytedance.lottietest.goldbox.model.OpenableInfo
import com.bytedance.lottietest.goldbox.model.RewardType
import com.bytedance.lottietest.goldbox.model.UnOpenableInfo

/**
 * 2021/7/19 23:50 LiSen
 * 金币宝箱
 */
class GoldCoinBox : FrameLayout {

    private val boxLottieUrl =
        "https://sf1-hscdn-tos.pstatp.com/obj/toutiao-cdn/gold_coin_box_shake_and_open.zip"
    private val shiningLottieUrl =
        "https://sf1-hscdn-tos.pstatp.com/obj/toutiao-cdn/gold_coin_box_shining.zip"
    private val droppingLottieUrl =
        "https://sf1-hscdn-tos.pstatp.com/obj/toutiao-cdn/gold_coin_box_dropping_coin.zip"
    private val urls = listOf(boxLottieUrl, shiningLottieUrl, droppingLottieUrl)

    companion object {
        private const val TAG = "GoldCoinBox"
    }

    enum class BoxState{
        Openable, UnOpenable
    }

    private lateinit var boxLayout: FrameLayout
    private lateinit var timeText: TextView
    lateinit var cancelBtn: ImageView
        private set
    private lateinit var boxLottieView: LottieAnimationView
    private lateinit var boxImageView: ImageView

    private lateinit var popupTips: View
    private lateinit var popupText: TextView
    private lateinit var popupWindow: PopupWindow

    private lateinit var lastState: BoxState
    private lateinit var state: BoxState

    private var fullScreenContainer: FullScreenContainer? = null

    var boxOpenListener: BoxOpenListener? = null
    var coinIncomeDialogCallback: ICoinIncomeDialogCallback? = null

    val countDownObserver = Observer<String> {
        if (!TextUtils.isEmpty(it)) {
            timeText.text = it
            lastState = if(!this::state.isInitialized){
                BoxState.UnOpenable
            }else{state}
            state = BoxState.UnOpenable
        } else {
            timeText.text = resources.getString(R.string.can_get_coin)
            lastState = if(!this::state.isInitialized){
                BoxState.Openable
            }else{state}
            state = BoxState.Openable
            if(popupWindow.isShowing) popupWindow.dismiss()
            if ((fullScreenContainer == null || fullScreenContainer?.isShowing == false) && lastState== BoxState.UnOpenable){
                boxLottieView.cancelAnimation()
                shakeWhenOpenable()
            }
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        Log.d(TAG, "init")

        LayoutInflater.from(context).inflate(R.layout.layout_gold_coin_box, this, true)

        for (url in urls) {
            LottieCompositionFactory.fromUrl(context, url)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        bindViews()
        initViews()
    }

    private fun bindViews() {
        timeText = findViewById(R.id.timer_text)
        cancelBtn = findViewById(R.id.box_cancel_btn)
        boxImageView = findViewById(R.id.box_image_view)
        boxLottieView = findViewById(R.id.box_lottie_view)
        boxLayout = findViewById(R.id.box_layout)
        popupTips = LayoutInflater.from(context).inflate(R.layout.gold_box_unclickable_tips, null)
        popupText = popupTips.findViewById(R.id.popup_text)
    }

    private fun initViews() {
        popupWindow = PopupWindow(popupTips, WRAP_CONTENT, WRAP_CONTENT, false)
        popupWindow.animationStyle = R.style.popup_anim

        cancelBtn.setOnClickListener {
            this.visibility = View.GONE
        }
        switchLottieVisibility(false)

        boxLottieView.setAnimationFromUrl(boxLottieUrl)
        boxLottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                switchLottieVisibility(true)
            }

            override fun onAnimationEnd(animation: Animator?) {
                switchLottieVisibility(false)
            }

            override fun onAnimationCancel(animation: Animator?) {
                switchLottieVisibility(false)
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })

        boxLayout.setOnClickListener {
            if (state == BoxState.Openable) {
                boxOpenListener?.onOpenable(object : BoxInfoCallback<OpenableInfo> {
                    override fun onSuccess(info: OpenableInfo) {
                        animateOpenBox(info)
                    }

                    override fun onFailed(error: String) {
                        Log.d(TAG, error)
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                boxOpenListener?.onUnOpenable(object : BoxInfoCallback<UnOpenableInfo> {
                    override fun onSuccess(info: UnOpenableInfo) {
                        // 点击一次，抖动一次
                        shakeWhenUnOpenable()
                        // 弹气泡
                        showPopup(info)
                    }

                    override fun onFailed(error: String) {
                        Log.d(TAG, error)
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun switchLottieVisibility(show: Boolean){
        if(show){
            boxLottieView.visibility = View.VISIBLE
            boxImageView.visibility = View.INVISIBLE
        }else{
            boxLottieView.visibility = View.INVISIBLE
            boxImageView.visibility = View.VISIBLE
        }
    }

    private fun makeFullScreenContainer(
        openableInfo: OpenableInfo,
        lottiePosition: IntArray
    ): FullScreenContainer {
        return FullScreenContainer(
            context,
            openableInfo,
            isBoxInRightBottom(lottiePosition),
            lottiePosition,
            object :
                ICoinIncomeDialogCallback {
                override fun onOkClick() {
                    coinIncomeDialogCallback?.onOkClick()
                }

                override fun onCloseClick() {
                    coinIncomeDialogCallback?.onCloseClick()
                }

                override fun onDismiss() {
                    if (state == BoxState.Openable) {
                        shakeWhenOpenable()
                    } else {
                        switchLottieVisibility(false)
                    }
                    coinIncomeDialogCallback?.onDismiss()
                }
            },
            object : FullScreenContainer.LottieAddCallback {
                override fun onLottieAdd() {
                    boxImageView.visibility = INVISIBLE
                }
            })
    }

    fun animateOpenBox(openableInfo: OpenableInfo) {
        val lottiePosition = intArrayOf(0, 0)
        boxLottieView.getLocationOnScreen(lottiePosition)
        boxLottieView.cancelAnimation()
        fullScreenContainer = makeFullScreenContainer(openableInfo, lottiePosition)
        fullScreenContainer?.show()
    }

    /**
     * 判断宝箱是否在右下角
     * 初始位置在右下角，判断是否被拖动过
     */
    private fun isBoxInRightBottom(position: IntArray): Boolean {
        val height = getScreenRealHeight(context as? Activity)
        return position[1] > height / 2
    }

    /**
     * 宝箱不可点击时，播放区间：0-60帧；
     * 播放一次，并停留在60帧；
     * 如果用户点击，再从0帧 - 60帧播放一次；
     */
    private fun shakeWhenUnOpenable() {

        Log.d(TAG, "animateWhenUnOpenable")

        if (!boxLottieView.isAnimating) {
            boxImageView.visibility = View.VISIBLE
            boxLottieView.visibility = View.VISIBLE
            boxLottieView.setMinAndMaxFrame(0, 60)
            boxLottieView.playAnimation()
        }

    }

    /**
     * 宝箱可以点击时，播放区间：120-180帧；
     * 循环播放；
     */
    private fun shakeWhenOpenable() {
        Log.d(TAG, "animateWhenOpenable")

        if (!boxLottieView.isAnimating) {
            boxImageView.visibility = View.VISIBLE
            boxLottieView.visibility = View.VISIBLE
            boxLottieView.setMinAndMaxFrame(120, 180)
            boxLottieView.playAnimation()
        }
    }

    private fun showPopup(unOpenableInfo: UnOpenableInfo) {
        if (!popupWindow.isShowing) {
            popupText.text = String.format(
                resources.getString(R.string.popup_text), unOpenableInfo.nextRewardAmount,
                if (unOpenableInfo.nextRewardType == RewardType.CRASH) {
                    resources.getString(R.string.popup_crash_postfix)
                } else {
                    resources.getString(R.string.popup_coin_postfix)
                }
            )
            val widthMeasureSpec =
                View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST)
            val heightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST)

            popupTips.measure(widthMeasureSpec, heightMeasureSpec)

            popupWindow.showAsDropDown(
                this,
                -(popupTips.measuredWidth - 84.dpInt) / 2,
                -(84.dpInt + 21.dpInt)
            )
            handler.postDelayed(
                {popupWindow.dismiss()},
                1000L
            )
        }
    }

}

