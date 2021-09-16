package com.bytedance.lottietest.goldbox.model

/**
 * 可领取态时所需信息
 * 奖励类型 奖励金额 下次冷却时长
 */
data class OpenableInfo(val rewardType: RewardType, val rewardAmount: Number, val nextRewardDuration: Long)