package com.bytedance.dragfloatingbuttontest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import org.jetbrains.annotations.NotNull;


/**
 * 可退拽自动吸边Layout，只能有最多一个子View
 */
public class DragRewardVideoLayout extends IDragRewardVideoLayout {
    private float mLastRawX;
    private float mLastRawY;
    private boolean isDrag = false;
    private int mRootMeasuredWidth = 0;
    private int mRootMeasuredHeight = 0;
    private int mRootTopY = 0;
    private boolean customIsAttach;
    private boolean customIsDrag;
    /**
     * 吸附在边上的边距
     */
    private int mHorizontalMargin;

    public DragRewardVideoLayout(Context context) {
        super(context);
    }

    public DragRewardVideoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public DragRewardVideoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    public void setDragEnable(boolean enable) {
        customIsDrag = enable;
    }

    /**
     * 初始化自定义属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AttachButton);
        customIsAttach = typedArray.getBoolean(R.styleable.AttachButton_customIsAttach, true);
        customIsDrag = typedArray.getBoolean(R.styleable.AttachButton_customIsDrag, true);
        typedArray.recycle();
    }

    private int mBottomMargin = 0;

    //距离底部的距离，无论如何滑动，都不会到底部这部分区域
    public void setBottomBound(int bottomMargin) {
        mBottomMargin = bottomMargin;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);

        getParent().requestDisallowInterceptTouchEvent(true);

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (customIsDrag) {
            float mRawX = ev.getRawX();
            float mRawY = ev.getRawY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isDrag = false;
                    mLastRawX = mRawX;
                    mLastRawY = mRawY;
                    ViewGroup mViewGroup = (ViewGroup) getParent();
                    if (mViewGroup != null) {
                        int[] location = new int[2];
                        mViewGroup.getLocationInWindow(location);
                        mRootMeasuredHeight = mViewGroup.getMeasuredHeight();
                        mRootMeasuredWidth = mViewGroup.getMeasuredWidth();
                        mRootTopY = location[1];
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mRawX >= 0 && mRawX <= mRootMeasuredWidth && mRawY >= mRootTopY && mRawY <= (mRootMeasuredHeight + mRootTopY)) {
                        float differenceValueX = mRawX - mLastRawX;
                        float differenceValueY = mRawY - mLastRawY;
                        if (!isDrag) {
                            if (Math.sqrt(differenceValueX * differenceValueX + differenceValueY * differenceValueY) < 2) {
                                isDrag = false;
                            } else {
                                isDrag = true;
                            }
                        }
                        float ownX = getX();
                        float ownY = getY();
                        float endX = ownX + differenceValueX;
                        float endY = ownY + differenceValueY;
                        //  获取最大移动范围
                        float maxX = mRootMeasuredWidth - getWidth();
                        float maxY = mRootMeasuredHeight - getHeight() - mBottomMargin;
                        // 边界检测
                        endX = endX < 0 ? 0 : Math.min(endX, maxX);
                        endY = endY < 0 ? 0 : Math.min(endY, maxY);
                        // 开始移动
                        setX(endX);
                        setY(endY);
                        // 记录位置
                        mLastRawX = mRawX;
                        mLastRawY = mRawY;
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    if (customIsAttach) {
                        //判断是否为点击事件
                        if (isDrag) {
                            float center = mRootMeasuredWidth / 2;
                            if (mLastRawX <= center) {
                                //向左贴边
                                DragRewardVideoLayout.this.animate()
                                        .setDuration(200)
                                        .x(mHorizontalMargin)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                //通知位置已发生变化
                                                if(getLocationChangedListener() != null) {
                                                    getLocationChangedListener().onLocationChanged((int)mLastRawX, (int)mLastRawY);
                                                }
                                            }
                                        })
                                        .start();
                                mLastRawX = mHorizontalMargin;
                            } else {
                                //向右贴边
                                DragRewardVideoLayout.this.animate()
                                        .setDuration(200)
                                        .x(mRootMeasuredWidth - getWidth() - mHorizontalMargin)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                //通知位置已发生变化
                                                if(getLocationChangedListener() != null) {
                                                    getLocationChangedListener().onLocationChanged((int)mLastRawX, (int)mLastRawY);
                                                }
                                            }
                                        })
                                        .start();
                                mLastRawX = mRootMeasuredWidth - getWidth() - mHorizontalMargin;
                            }
                        }
                    }
                    break;
            }
        }
        //是否拦截事件
        return isDrag ? isDrag : super.onTouchEvent(ev);
    }



    @Override
    public void setHorizontalMargin(int margin) {
        mHorizontalMargin = margin;
    }


    /**
     * 获取最终拖动的位置，如果两个值小于1，则表示没有拖动过，不更新。
     *
     * @return
     */
    @Override
    public void getLastLocation(@NotNull int[] res) {
        if (res == null || res.length != 2) {
            return;
        }
        res[0] = (int) mLastRawX;
        res[1] = (int) mLastRawY;
    }

    /**
     * 给子View设置OnClickListener后，ViewGroup不能正确处理事件，也就不能拖拽，
     * 设置子View为不可点击后，子View不会处理事件，让ViewGroup处理，
     * 然后ViewGroup代理子View的点击事件
     */
    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        child.setClickable(false);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child.performClick();
            }
        });
    }
}

