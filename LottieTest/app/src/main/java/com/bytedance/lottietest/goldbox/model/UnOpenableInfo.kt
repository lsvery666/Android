package com.bytedance.lottietest.goldbox.model

import com.bytedance.lottietest.goldbox.model.RewardType

/**
 * 不可领取态时所需信息
 * 下次奖励类型 下次奖励金额
 */
data class UnOpenableInfo(val nextRewardType: RewardType, val nextRewardAmount: Number)