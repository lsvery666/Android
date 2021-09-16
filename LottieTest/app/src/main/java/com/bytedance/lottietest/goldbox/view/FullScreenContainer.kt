package com.bytedance.lottietest.goldbox.view

import android.animation.Animator
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.bytedance.lottietest.MainActivity
import com.bytedance.lottietest.R
import com.bytedance.lottietest.goldbox.model.AnimatorRepo
import com.bytedance.lottietest.goldbox.callback.ICoinIncomeDialogCallback
import com.bytedance.lottietest.goldbox.model.OpenableInfo
import com.bytedance.lottietest.goldbox.model.RewardType
import com.bytedance.lottietest.goldbox.utils.Utils.getNavigationBarHeight
import com.bytedance.lottietest.goldbox.utils.Utils.getScreenRealHeight
import com.bytedance.lottietest.goldbox.utils.Utils.getScreenRealWidth
import com.bytedance.news.ug.luckycat.goldbox.utils.FormatUtils
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread




class FullScreenContainer(
    context: Context,
    private val openableInfo: OpenableInfo,
    private val isBoxInRightBottom: Boolean,
    val lottiePosition: IntArray,
    private var mCoinIncomeCallback: ICoinIncomeDialogCallback,
    private var mLottieAddCallback: LottieAddCallback
) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {

    private val openingBoxLottieUrl =
        "https://sf1-hscdn-tos.pstatp.com/obj/toutiao-cdn/gold_coin_box_shake_and_open.zip"
    private val droppingFlowersLottieUrl =
        "https://sf1-hscdn-tos.pstatp.com/obj/toutiao-cdn/gold_coin_box_shining.zip"
    private val droppingCoinsLottieUrl =
        "https://sf1-hscdn-tos.pstatp.com/obj/toutiao-cdn/gold_coin_box_dropping_coin.zip"
    private val urls = listOf(openingBoxLottieUrl, droppingFlowersLottieUrl, droppingCoinsLottieUrl)

    private val TAG = "FullScreenContainer"
    private val mContext = context
    private lateinit var root: FrameLayout
    private lateinit var backgroundBlack: View
    private lateinit var coinIncomeDialog: LinearLayout
    private lateinit var whiteCover: View
    private lateinit var congratulationText: TextView
    private lateinit var incomeLayout: RelativeLayout
    private lateinit var incomeText: LinearLayout
    private lateinit var scrollNumber: MultiScrollNumber
    private lateinit var incomePostfix: TextView
    private lateinit var withDrawTag: ImageView
    private lateinit var droppingFlowersLottieView: LottieAnimationView
    private lateinit var droppingCoinsLottieView: LottieAnimationView

    private lateinit var boxImageView: ImageView
    private lateinit var boxLottieView: LottieAnimationView

    private lateinit var nextTreasureText: TextView
    private lateinit var coinIncomeCloseBtn: ImageView
    private lateinit var coinIncomeOkBtn: FrameLayout

    val Float.dp: Float
        get() = (context.resources.displayMetrics.density * this) + 0.5f

    val Float.dpInt: Int
        get() = this.dp.toInt()

    val Int.dp: Float
        get() = this.toFloat().dp

    val Int.dpInt: Int
        get() = this.dp.toInt()

    init {

        for (url in urls) {
            LottieCompositionFactory.fromUrl(context, url)
        }

        initWindow()

        setContentView(R.layout.dialog_full_screen_lottie_view)

        bindViews()
        initViews()
    }

    interface LottieAddCallback {
        fun onLottieAdd()
    }

    private fun initWindow() {
        window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun bindViews() {
        root = findViewById(R.id.root_layout)
        backgroundBlack = findViewById(R.id.background_black)
        coinIncomeDialog = findViewById(R.id.dialog_coin_income)
        whiteCover = findViewById(R.id.white_cover)
        congratulationText = findViewById(R.id.congratulation_text)
        incomeLayout = findViewById(R.id.income_layout)
        incomeText = findViewById(R.id.income_text)
        scrollNumber = incomeText.findViewById(R.id.income_amount)
        incomePostfix = incomeText.findViewById(R.id.income_postfix)
        withDrawTag = incomeLayout.findViewById(R.id.withDrawTag)
        droppingFlowersLottieView = findViewById(R.id.shining_lottie_view)
        droppingCoinsLottieView = findViewById(R.id.dropping_lottie_view)
        nextTreasureText = coinIncomeDialog.findViewById(R.id.next_treasure)
        coinIncomeCloseBtn = coinIncomeDialog.findViewById(R.id.coin_income_close_btn)
        coinIncomeOkBtn = coinIncomeDialog.findViewById(R.id.coin_income_ok_btn)
    }

    private fun initViews() {
        whiteCover.alpha = 0f
        coinIncomeDialog.alpha = 0f
        incomeLayout.alpha = 0f
        congratulationText.alpha = 0f

        scrollNumber.initNum(FormatUtils.num2random(openableInfo.rewardAmount), false)

        when (openableInfo.rewardType) {
            RewardType.CRASH -> {
                incomePostfix.text = mContext.resources.getString(R.string.dialog_crash_postfix)
                withDrawTag.visibility = View.VISIBLE
            }
            RewardType.COIN -> {
                incomePostfix.text = mContext.resources.getString(R.string.dialog_coin_postfix)
                withDrawTag.visibility = View.GONE
            }
        }

        nextTreasureText.text = String.format(
            mContext.resources.getString(
                R.string.next_treasure,
                FormatUtils.mills2str(openableInfo.nextRewardDuration, true)
            )
        )

        coinIncomeOkBtn.setOnClickListener {
            Log.d(TAG, "onOkClick")
            mCoinIncomeCallback.onOkClick()
            coinIncomeDialogExit()
        }

        coinIncomeCloseBtn.setOnClickListener {
            Log.d(TAG, "onCloseClick")
            mCoinIncomeCallback.onCloseClick()
            coinIncomeDialogExit()
        }
    }

    private fun addLottieView(lottiePosition: IntArray) {
        Log.d(TAG, "${lottiePosition[0]}, ${lottiePosition[1]}")

        val realWidth = 300.dpInt
        val realHeight = 300.dpInt
        val initWidth = 84.dpInt
        val initHeight = 84.dpInt

        boxLottieView = LottieAnimationView(mContext).apply {
            scaleX = (initWidth / realWidth).toFloat()
            scaleY = (initHeight / realHeight).toFloat()
        }
        val lp = FrameLayout.LayoutParams(realWidth, realHeight).apply {
            this.gravity = Gravity.TOP or Gravity.LEFT
            this.leftMargin = lottiePosition[0] - (realWidth - initWidth) / 2
            this.topMargin = lottiePosition[1] - (realHeight - initHeight) / 2
        }

        boxLottieView.addAnimatorUpdateListener {
            Log.d(TAG, it.animatedValue.toString())
        }

        root.addView(boxLottieView, 1, lp)

        boxImageView = ImageView(mContext).apply {
            scaleX = 0.28f
            scaleY = 0.28f
            setImageResource(R.drawable.box_bg)
        }
        root.addView(boxImageView, 1, lp)

        mLottieAddCallback.onLottieAdd()
    }

    private fun computeTranslation(): FloatArray {
        val screenWidth: Int = getScreenRealWidth(mContext)
        val screenHeight: Int = getScreenRealHeight(mContext) - getNavigationBarHeight(mContext)
        val boxLottieCenter = floatArrayOf(lottiePosition[0] + 42.dp, lottiePosition[1] + 42.dp)
        val destPosition = floatArrayOf(screenWidth / 2f, screenHeight / 2f - 48.dp)

        return floatArrayOf(
            destPosition[0] - boxLottieCenter[0],
            destPosition[1] - boxLottieCenter[1]
        )
    }

    override fun dismiss() {
        super.dismiss()
        mCoinIncomeCallback.onDismiss()
    }

    private fun coinIncomeDialogExit() {

        AnimatorRepo.dialogExitAnimator(root, object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                dismiss()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        }).start()

    }

    private fun animateLocal(
        translation: FloatArray,
        animatorListener: Animator.AnimatorListener?
    ) {
        val set = AnimatorRepo.totalAnimatorSet(
            backgroundBlack,
            boxLottieView,
            boxImageView,
            coinIncomeDialog,
            congratulationText,
            incomeLayout,
            whiteCover,
            translation,
            isBoxInRightBottom,
            animatorListener
        )
        set.start()
    }


    private fun showLottie(
        context: Context,
        lottieView: LottieAnimationView,
        lottieComposition: LottieComposition?,
        delayMillis: Long,
        frames: IntArray? = null,
        fillAfter: Boolean = false,
        startAction: () -> Unit = {},
        endAction: () -> Unit = {}
    ) {
        if (lottieComposition == null) {
            Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show()
        } else {
            lottieView.apply {
                setComposition(lottieComposition)
                enableMergePathsForKitKatAndAbove(true)
                if (frames != null) setMinAndMaxFrame(frames[0], frames[1]);
                addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        startAction()
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        if (!fillAfter) visibility = View.INVISIBLE
                        endAction()
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                })
            }
            postDelayed({lottieView.playAnimation()}, delayMillis)
        }
    }

    private fun postDelayed(runnable: () -> Unit, delayMillis: Long = 0L) {
        root.postDelayed(runnable, delayMillis)
    }

    private fun showOpeningBoxLottie(
        context: Context,
        lottieComposition: LottieComposition?,
        delayMillis: Long
    ) {
        showLottie(
            context,
            boxLottieView,
            lottieComposition,
            delayMillis,
            intArrayOf(276, 372), // 276, 372
            fillAfter = true,
            startAction = {
                boxImageView.visibility = View.GONE
            })
    }

    private fun showDroppingFlowersLottie(
        context: Context,
        lottieComposition: LottieComposition?,
        delayMillis: Long
    ) {
        showLottie(
            context,
            droppingFlowersLottieView,
            lottieComposition,
            delayMillis,
            fillAfter = false
        )
    }

    private fun showDroppingCoinsLottie(
        context: Context,
        lottieComposition: LottieComposition?,
        delayMillis: Long
    ) {
        showLottie(
            context,
            droppingCoinsLottieView,
            lottieComposition,
            delayMillis,
            fillAfter = false
        )
    }

    override fun show() {
        super.show()
        tryLoadLottieCompositionAndAnimate(
            mContext, urls, {
                addLottieView(lottiePosition)
                animateLocal(computeTranslation(), null)
                // 0.25s后宝箱开始移动
                // 宝箱开始移动后，宝箱打开动画
                showOpeningBoxLottie(mContext, it[openingBoxLottieUrl], 250)
                // 宝箱开始移动后0.4s，撒花动画
                showDroppingFlowersLottie(mContext, it[droppingFlowersLottieUrl], 650)
                // 宝箱开始移动后0.55s，跳出金币动画
                showDroppingCoinsLottie(mContext, it[droppingCoinsLottieUrl], 800)
                // 宝箱开始移动后1s，滚动数字动画
                postDelayed({scrollNumber.scrollTo(openableInfo.rewardAmount.toString())}, 1250)
            },
            {
                Toast.makeText(mContext, "网络开小差了，请稍后再试", Toast.LENGTH_SHORT).show()
                super.dismiss()
            })
    }

    private fun tryLoadLottieCompositionAndAnimate(
        context: Context,
        urls: List<String>,
        succeedAction: (lottieCompositions: Map<String, LottieComposition>) -> Unit,
        failedAction: () -> Unit
    ) {
        thread{
            val countDownLatch = CountDownLatch(urls.size)
            val result = mutableMapOf<String, LottieComposition>()
            urls.forEach { url ->
                LottieCompositionFactory.fromUrl(context, url).addListener { lottieComposition ->
                    result[url] = lottieComposition
                    countDownLatch.countDown()
                }
            }

            countDownLatch.await(2000, TimeUnit.MILLISECONDS)
            if (countDownLatch.count == 0L) {
                postDelayed( {succeedAction(result)})
            } else {
                postDelayed(failedAction)
            }
        }
    }
}