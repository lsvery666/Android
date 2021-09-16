package com.bytedance.lottietest.goldbox.listener

import com.bytedance.lottietest.goldbox.callback.BoxInfoCallback
import com.bytedance.lottietest.goldbox.model.OpenableInfo
import com.bytedance.lottietest.goldbox.model.UnOpenableInfo

/**
 * 宝箱可领取态和不可领取态的点击事件
 * 业务方需要调用BoxInfoCallback中的onSuccess和onFailed
 */
interface BoxOpenListener {

    fun onOpenable(boxInfoCallback: BoxInfoCallback<OpenableInfo>)

    fun onUnOpenable(boxInfoCallback: BoxInfoCallback<UnOpenableInfo>)

}

