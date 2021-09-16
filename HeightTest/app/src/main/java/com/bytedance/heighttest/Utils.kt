package com.bytedance.heighttest

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import android.view.ViewGroup

object Utils {

    /**
     * 屏幕高度
     */
    fun getScreenRealHeight(activity: Activity): Int{
        var displayMetrics = DisplayMetrics()

        // activity.windowManager.defaultDisplay过时， 使用context.display
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            activity.display?.getRealMetrics(displayMetrics)
        }else{
            displayMetrics = activity.resources.displayMetrics
        }

        return displayMetrics.heightPixels
    }

    /**
     * 屏幕高度
     */
    fun getScreenHeight(activity: Activity): Int{
        var displayMetrics = DisplayMetrics()

        activity.display?.getMetrics(displayMetrics)

        return displayMetrics.heightPixels
    }

    /**
     * 导航栏高度
     */
    fun getNavigationBarHeight(activity: Activity): Int {

        var navBarHeight = 0
        try {
            val vp = activity.window.decorView as ViewGroup
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
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
        return navBarHeight
    }

    /**
     * 标题栏高度
     */
    fun getStatusBarHeight(activity: Activity): Int{

        var statusBarHeight = -1

        //获取status_bar_height资源的ID
        val resourceId: Int = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight =  activity.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

}