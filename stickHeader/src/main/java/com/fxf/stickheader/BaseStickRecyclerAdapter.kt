package com.fxf.stickheader

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * 适配器基类，持有分割线实例子类T
 * 参考demo 中子类 FansRecyclerAdapter
 */
public abstract class BaseStickRecyclerAdapter<T : BaseStickBean?, H : BaseStickRecyclerAdapter.RecyclerViewHolder?>(var mContext: Context, var mList: List<T>?) : RecyclerView.Adapter<H>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        return onCreateViewHolder(parent, viewType, false)
    }

    abstract fun onCreateViewHolder(parent: ViewGroup?, viewType: Int, isItem: Boolean): H
    override fun getItemCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    /**
     * 判断position对应的Item是否是组的第一项
     *
     * @param position
     * @return
     */
    fun isItemHeader(position: Int): Boolean {
        if (position < 0) return false
        if (mList!!.size - position <= 0)return false
        return if (position == 0) {
            true
        } else {
            val lastGroupName = mList!![position - 1]!!.groupName
            val currentGroupName = mList!![position]!!.groupName
            //判断上一个数据的组别和下一个数据的组别是否一致，如果不一致则是不同组，也就是为第一项（头部）
            lastGroupName != currentGroupName
        }
    }

    /**
     * 获取position对应的Item组名
     *
     * @param position
     * @return
     */
    fun getGroupName(position: Int): String {
        return mList!![position]!!.groupName
    }

    /**
     * 自定义ViewHolder
     */
    abstract class RecyclerViewHolder(parent: ViewGroup, layoutId: Int) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

}