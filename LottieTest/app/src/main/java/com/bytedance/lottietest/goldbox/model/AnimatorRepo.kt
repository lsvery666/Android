package com.bytedance.lottietest.goldbox.model

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.animation.*
import com.bytedance.lottietest.goldbox.utils.dp

/**
 * 动画库
 * 采用ObjectAnimator实现
 */
@SuppressLint("ObjectAnimatorBinding")
object AnimatorRepo {

    private val TAG = "FullScreenContainer"

    /**
     * 背景透明度渐变动画
     */
    fun backgroundAlphaAnimator(target: Any) =
        ObjectAnimator.ofFloat(target, "alpha", 0f, 0.5f).apply {
            duration = 400
            interpolator = LinearInterpolator()
        }

    /**
     * 宝箱升起动画
     */
    fun boxTranslationXAnimator(target: Any, translationX: Float): ObjectAnimator =
        ObjectAnimator.ofFloat(target, "translationX", translationX).apply {
            duration = 500
//            interpolator = CubicBezierInterpolator(0.4f, 0.8f, 0.74f, 1f)
        }

    fun boxTranslationYAnimator1(target: Any, translationY: Float): ObjectAnimator =
        ObjectAnimator.ofFloat(target, "translationY", translationY - 165.dp).apply {
            duration = 300
//            interpolator = CubicBezierInterpolator(0.4f, 0.8f, 0.74f, 1f)
        }

    fun boxTranslationYAnimator2(target: Any, translationY: Float): ObjectAnimator =
        ObjectAnimator.ofFloat(target, "translationY", translationY).apply {
            duration = 800
            interpolator = BounceInterpolator()
        }

    /**
     * 宝箱不在右下角时
     */
    fun boxTranslationYAnimator(target: Any, translationY: Float) =
        ObjectAnimator.ofFloat(target, "translationY", translationY).apply {
            duration = 1100
            interpolator = BounceInterpolator()
        }

    fun boxScaleXAnimator1(target: Any): ObjectAnimator =
        ObjectAnimator.ofFloat(target, "scaleX", 0.28f, 270 / 300f).apply {
            duration = 500
//            interpolator = CubicBezierInterpolator(0.4f, 0.8f, 0.74f, 1f)
        }

    fun boxScaleXAnimator2(target: Any): ObjectAnimator =
        ObjectAnimator.ofFloat(target, "scaleX", 270 / 300f, 280 / 300f).apply {
            duration = 750
//            interpolator = SpringInterpolator(0.3f)
        }

    fun boxScaleYAnimator1(target: Any): ObjectAnimator =
        ObjectAnimator.ofFloat(target, "scaleY", 0.28f, 295 / 300f).apply {
            duration = 500
//            interpolator = CubicBezierInterpolator(0.4f, 0.8f, 0.74f, 1f)
        }

    fun boxScaleYAnimator2(target: Any): ObjectAnimator =
        ObjectAnimator.ofFloat(target, "scaleY", 295 / 300f, 280 / 300f).apply {
            duration = 750
//            interpolator = SpringInterpolator(0.3f)
        }

    /**
     * 恭喜获得金币Dialog进入动画
     */
    fun dialogEnterAnimator(target: Any): ObjectAnimator {
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f)
        val alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(target, scaleX, scaleY, alpha)
        animator.duration = 450
//        animator.interpolator = CubicBezierInterpolator(0.3f, 1.3f, 0.3f, 1f)
        return animator
    }

    /**
     * 恭喜获得金币Dialog退出动画
     */
    fun dialogExitAnimator(
        target: Any,
        animatorListener: Animator.AnimatorListener?
    ): ObjectAnimator {
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f)
        val alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(target, scaleX, scaleY, alpha)
        animator.duration = 150
//        animator.interpolator = CubicBezierInterpolator(0.48f, 0.04f, 0.52f, 0.96f)
        animator.addListener(animatorListener)
        return animator
    }

    /**
     * "恭喜你获得"文字出现
     */
    fun congratulationTextAnimator(target: Any): ObjectAnimator {
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.6f, 1.1f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.6f, 1.1f, 1f)
        val alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(target, scaleX, scaleY, alpha).apply {
            duration = 200
//            interpolator = CubicBezierInterpolator(0.42f, 0f, 0.58f, 1f)
        }
        return animator
    }

    /**
     * "金币"文字出现
     * 参数与"恭喜你获得"一致
     */
    fun coinTextAnimator(target: Any): ObjectAnimator {
        return congratulationTextAnimator(target)
    }

    /**
     * 白色的蒙层
     */
    fun whiteCoverAnimator(target: Any): ObjectAnimator =
        ObjectAnimator.ofFloat(target, "translationY", -47.dp).apply {
            duration = 400
            interpolator = DecelerateInterpolator(1.5f)
            addListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {
                    (target as View).alpha = 1f
                }

                override fun onAnimationEnd(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }
            })
        }

    /**
     * 合集
     */
    fun totalAnimatorSet(
        background: Any,
        boxLottie: View,
        boxImage:Any,
        dialog: Any,
        congratulationText: Any,
        incomeLayout: Any,
        whiteCover: Any,
        translation: FloatArray,
        isBoxInRightBottom: Boolean,
        animatorListener: Animator.AnimatorListener?
    ): AnimatorSet {
        Log.d("FullScreenContainer", "${boxLottie.width}, ${boxLottie.width}")
        val animatorSet = AnimatorSet()
        // step1: t = 0s时，背景色变化
        animatorSet.play(backgroundAlphaAnimator(background))
        // step2: t = 0.25s时，宝箱开始移动
        if (isBoxInRightBottom) {
            animatorSet
                .play(boxTranslationXAnimator(boxLottie, translation[0])).after(250)
                .with(boxTranslationYAnimator1(boxLottie, translation[1])).before(
                    boxTranslationYAnimator2(boxLottie, translation[1])
                )
                .with(boxScaleYAnimator1(boxLottie)).before(boxScaleYAnimator2(boxLottie))
                .with(boxScaleXAnimator1(boxLottie)).before(boxScaleXAnimator2(boxLottie))

            // 兜底：Lottie还没有加载成功，先移动静态图
            animatorSet
                .play(boxTranslationXAnimator(boxImage, translation[0])).after(250)
                .with(boxTranslationYAnimator1(boxImage, translation[1])).before(
                    boxTranslationYAnimator2(boxImage, translation[1])
                )
                .with(boxScaleYAnimator1(boxImage)).before(boxScaleYAnimator2(boxImage))
                .with(boxScaleXAnimator1(boxImage)).before(boxScaleXAnimator2(boxImage))

        } else {
            animatorSet
                .play(boxTranslationXAnimator(boxLottie, translation[0])).after(250)
                .with(boxTranslationYAnimator(boxLottie, translation[1]))
                .with(boxScaleYAnimator1(boxLottie)).before(boxScaleYAnimator2(boxLottie))
                .with(boxScaleXAnimator1(boxLottie)).before(boxScaleXAnimator2(boxLottie))

            // 兜底：Lottie还没有加载成功，先移动静态图
            animatorSet
                .play(boxTranslationXAnimator(boxImage, translation[0])).after(250)
                .with(boxTranslationYAnimator(boxImage, translation[1]))
                .with(boxScaleYAnimator1(boxImage)).before(boxScaleYAnimator2(boxImage))
                .with(boxScaleXAnimator1(boxImage)).before(boxScaleXAnimator2(boxImage))
        }

        // step3: t = 1.05s时，弹窗出现（宝箱开始移动后0.8s)
        animatorSet.play(dialogEnterAnimator(dialog)).after(1050)
        // step4: t = 1.15s时，"恭喜你获得"文字出现（宝箱开始移动后0.9s)
        animatorSet.play(congratulationTextAnimator(congratulationText)).after(1150)
        // step5: t = 1.25s时，"金币"文字出现（宝箱开始移动后1s)
        animatorSet.play(coinTextAnimator(incomeLayout)).after(1250)
        // step6: t = 1.5s时，白色蒙层上移，遮住宝箱下半部分（弹窗完全出现后）
        animatorSet.play(whiteCoverAnimator(whiteCover)).after(1500)
        if(animatorListener!=null) animatorSet.addListener(animatorListener)
        return animatorSet
    }

}