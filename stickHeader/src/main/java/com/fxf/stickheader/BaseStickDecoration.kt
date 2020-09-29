package com.fxf.stickheader

import android.content.Context
import android.graphics.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.util.Log
import android.view.View

/**
 * 分割线基类,添加stickHeader主要逻辑
 * 参考demo中子类 FansStickDecoration
 */
abstract class BaseStickDecoration<T : BaseStickRecyclerAdapter<*, *>?>(context: Context) : RecyclerView.ItemDecoration() {
    //头部的高
    private val mItemHeaderHeight: Int
    private val mTextPaddingLeft: Int

    //画笔，绘制头部和分割线
    private val mItemHeaderPaint: Paint
    private val mTextPaint: Paint
    private val mLinePaint: Paint
    private val mTextRect: Rect

    //头部图片
    private val bitmapWidth: Int
    private val bitmapHeight: Int
    private val bitmapPaddingLeft: Int
    // 获取一个bitmap 得到宽高
    abstract val bitmap: Bitmap

    // 初始化头部icon
    abstract fun initIconGroups()

    val iconMap: MutableMap<String, Bitmap>
    val mContext: Context = context

    private fun getBitmapByGroupName(groupName: String) :Bitmap{
        return iconMap[groupName]!!
    }

    init {
        mItemHeaderHeight = dp2px(context, 40f)
        mTextPaddingLeft = dp2px(context, 6f)
        mTextRect = Rect()
        mItemHeaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mItemHeaderPaint.color = Color.parseColor("#FF1C1C1E")
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.textSize = 46f
        mTextPaint.color = Color.WHITE
        mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLinePaint.color = Color.GRAY

        bitmapPaddingLeft = dp2px(context, 16f)
        iconMap = ArrayMap()
        initIconGroups()
        bitmapWidth = bitmap.width
        bitmapHeight = bitmap.height
    }

    /**
     * 绘制Item的分割线和组头
     *
     * @param c
     * @param parent
     * @param state
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter is BaseStickRecyclerAdapter<*, *>) {
            val adapter = parent.adapter as T
            val count = parent.childCount //获取可见范围内Item的总数
            for (i in 0 until count) {
                val view = parent.getChildAt(i)
                /**这里-2 为去除xRecyclerview顶部刷新view 和 添加的header 如果未设置header则减1 */
                val position = parent.getChildLayoutPosition(view) - 2
                val isHeader = adapter!!.isItemHeader(position)
                val left = parent.paddingLeft
                val right = parent.width - parent.paddingRight
                if (isHeader) {
                    c.drawRect(left.toFloat(), view.top - mItemHeaderHeight.toFloat(), right.toFloat(), view.top.toFloat(), mItemHeaderPaint)
                    c.drawBitmap(getBitmapByGroupName(adapter.getGroupName(position)), left + bitmapPaddingLeft.toFloat(), view.top - mItemHeaderHeight / 2 - (bitmapHeight / 2).toFloat(), mTextPaint)
                    mTextPaint.getTextBounds(adapter.getGroupName(position), 0, adapter.getGroupName(position).length, mTextRect)
                    c.drawText(adapter.getGroupName(position), left + mTextPaddingLeft + bitmapWidth + bitmapPaddingLeft.toFloat(), view.top - mItemHeaderHeight + mItemHeaderHeight / 2 + (mTextRect.height() / 2).toFloat(), mTextPaint)
                } else {
                    c.drawRect(left.toFloat(), view.top - 1.toFloat(), right.toFloat(), view.top.toFloat(), mLinePaint)
                }
            }
        }
    }

    /**
     * 绘制Item的顶部布局（吸顶效果）
     *
     * @param c
     * @param parent
     * @param state
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter is BaseStickRecyclerAdapter<*, *>) {
            val adapter = parent.adapter as T
            var position = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            var view = parent.findViewHolderForAdapterPosition(position)!!.itemView
            val cp = parent.getChildLayoutPosition(view)
            if (cp < 2)return //childPosition 位置小于2时返回，即为不绘制顶部的吸顶，等划过header以后再绘制
            position -=2 //child view 在父容器中的位置比在data内位置多两个（刷新view和header）所以减2
            val isHeader = adapter!!.isItemHeader(position + 1)
            val top = parent.paddingTop
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            if (isHeader) {
                val bottom = Math.min(mItemHeaderHeight, view.bottom)
                c.drawRect(left.toFloat(), top + view.top - mItemHeaderHeight.toFloat(), right.toFloat(), top + bottom.toFloat(), mItemHeaderPaint)
                mTextPaint.getTextBounds(adapter.getGroupName(position), 0, adapter.getGroupName(position).length, mTextRect)
                if (view.bottom > mItemHeaderHeight) {
                    Log.d("fuxiao","固定tab")
                    c.drawBitmap(getBitmapByGroupName(adapter.getGroupName(position)), left + bitmapPaddingLeft.toFloat(), top + mItemHeaderHeight / 2 - bitmapHeight / 2.toFloat(), mTextPaint)
                } else {
                    Log.d("fuxiao","逐渐隐藏")
                    c.drawBitmap(getBitmapByGroupName(adapter.getGroupName(position)), left + bitmapPaddingLeft.toFloat(), top + bottom - mItemHeaderHeight / 2 - (bitmapHeight / 2).toFloat(), mTextPaint)
                }
                c.drawText(adapter.getGroupName(position), left + mTextPaddingLeft + bitmapWidth + bitmapPaddingLeft.toFloat(), top + mItemHeaderHeight / 2 + mTextRect.height() / 2 - (mItemHeaderHeight - bottom).toFloat(), mTextPaint)
            } else {
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), top + mItemHeaderHeight.toFloat(), mItemHeaderPaint)
                c.drawBitmap(getBitmapByGroupName(adapter.getGroupName(position)), left + bitmapPaddingLeft.toFloat(), top + mItemHeaderHeight / 2 - bitmapHeight / 2.toFloat(), mTextPaint)
                mTextPaint.getTextBounds(adapter.getGroupName(position), 0, adapter.getGroupName(position).length, mTextRect)
                c.drawText(adapter.getGroupName(position), left + mTextPaddingLeft + bitmapWidth + bitmapPaddingLeft.toFloat(), top + mItemHeaderHeight / 2 + (mTextRect.height() / 2).toFloat(), mTextPaint)
            }
            c.save()
        }
    }

    /**
     * 设置Item的间距
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter is BaseStickRecyclerAdapter<*, *>) {
            val adapter = parent.adapter as T
            /**这里-2 为去除xRecyclerview顶部刷新view 和 添加的header 如果未设置header则减1 */
            val position = parent.getChildLayoutPosition(view) - 2
            val isHeader = adapter!!.isItemHeader(position)
            if (isHeader) {
                outRect.top = mItemHeaderHeight
            } else {
                outRect.top = 1
            }
        }
    }

    /**
     * dp转换成px
     */
    private fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    companion object {
        const val FUXIAO = "fuxiao"
    }
}