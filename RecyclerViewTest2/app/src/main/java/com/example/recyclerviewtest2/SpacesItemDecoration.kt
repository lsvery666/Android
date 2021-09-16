package com.example.recyclerviewtest2

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import java.util.*

class SpacesItemDecoration(
    //行数
    private val spanCount: Int,
    //间隔
    private val spaceValue: HashMap<String, Int>,
    //是否包含四周边距
    private val includeEdge: Boolean
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = spaceValue[LEFT_SPACE]!!
        outRect.top = spaceValue[TOP_SPACE]!!
        outRect.right = spaceValue[RIGHT_SPACE]!!
        outRect.bottom = spaceValue[BOTTOM_SPACE]!!
        //这里是关键，需要根据你有几行来判断
//        val position = parent.getChildAdapterPosition(view) // item position
//        val row = position % spanCount  // item row 0 1
//        if (includeEdge) {
//            outRect.left = spaceValue[LEFT_SPACE]!! - row * spaceValue[LEFT_SPACE]!! / spanCount
//            outRect.right = (row + 1) * spaceValue[RIGHT_SPACE]!! / spanCount
//            if (position < spanCount) { // top edge 判断是首行
//                outRect.top = spaceValue[TOP_SPACE]!!
//            }
//            outRect.bottom = spaceValue[BOTTOM_SPACE]!! // item bottom
//        } else {
//
//            outRect.right = spaceValue[RIGHT_SPACE]!! - (row + 1) * spaceValue[RIGHT_SPACE]!! / spanCount
//            if (position >= spanCount) { //不是首列
//                outRect.left = spaceValue[LEFT_SPACE]!! // item left
//            }
//            //只有首列设置左边距
//            //if (position >= spanCount) {//不是首行
//            //   outRect.top = spaceValue.get(TOP_SPACE); // item top
//            //}else {
//            //   outRect.top = 20; //首行
//            //}
//
//            //只有下边距
////                if (position > (parent.getAdapter().getItemCount() - spanCount)-1) {
////                    outRect.bottom = 100;
////                }
//        }
    }

    companion object {
        const val TOP_SPACE = "top_space"
        const val BOTTOM_SPACE = "bottom_space"
        const val LEFT_SPACE = "left_space"
        const val RIGHT_SPACE = "right_space"
    }
}