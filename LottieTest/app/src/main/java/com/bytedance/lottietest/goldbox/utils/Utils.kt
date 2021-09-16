package com.bytedance.lottietest.goldbox.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.ViewGroup
import com.bytedance.lottietest.MainActivity
import kotlin.math.max
import kotlin.math.min

val Float.dp: Float
    get() = (4 * this) + 0.5f

val Float.dpInt: Int
    get() = this.dp.toInt()

val Int.dp: Float
    get() = this.toFloat().dp

val Int.dpInt: Int
    get() = this.dp.toInt()

object Utils {

    fun getScreenRealHeight(context: Context?): Int {
        if (context == null) {
            return 0
        }
        var dm: DisplayMetrics? = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context != null) {
            (context as? Activity)?.let{it.windowManager.defaultDisplay.getRealMetrics(dm)}
        } else {
            dm = context.resources.displayMetrics
        }
        return if (dm == null) 0 else max(dm.heightPixels, dm.widthPixels)
    }

    fun getNavigationBarHeight(activity: Context?): Int {
        if (activity == null) {
            return 0
        }
        var navBarHeight = 0
        val activity = (activity as? Activity)!!
        try {
            val vp = activity.window.decorView as ViewGroup
            if (vp != null) {
                for (i in 0 until vp.childCount) {
                    vp.getChildAt(i).context.packageName
                    if (vp.getChildAt(i).id != -1 && "navigationBarBackground" == activity.resources.getResourceEntryName(
                            vp.getChildAt(i).id
                        )
                    ) {
                        val resources = activity.resources
                        val resourceId =
                            resources.getIdentifier("navigation_bar_height", "dimen", "android")
                        if (resourceId > 0) {
                            //获取NavigationBar的高度
                            navBarHeight = resources.getDimensionPixelSize(resourceId)
                        }
                    }
                }
            }
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
        return navBarHeight
    }

    fun getScreenRealWidth(context: Context?): Int {
        if (context == null) {
            return 0
        }
        var dm: DisplayMetrics? = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context != null) {
            (context as? Activity)?.let{it.windowManager.defaultDisplay.getRealMetrics(dm)}
        } else {
            dm = context.resources.displayMetrics
        }
        return if (dm == null) 0 else min(dm.widthPixels, dm.heightPixels)
    }
}