package com.fxf.view.demo

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fxf.stickheader.BaseStickRecyclerAdapter

class FansRecyclerAdapter(context: Context?, list: List<FansMemberBean?>?) : BaseStickRecyclerAdapter<FansMemberBean?, FansRecyclerAdapter.ViewHolder?>(context!!, list) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int, isItem: Boolean): ViewHolder {
        return ViewHolder(parent, R.layout.item_star_list)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val text = mList!![position]!!.name
        val bean = mList!![position]!!
        holder!!.mHeadName.text = text
        holder.mIntimateValue.text = bean.intimateValue
        holder.mHeadImg.setImageResource(R.mipmap.ic_launcher)
    }

    /**
     * 自定义ViewHolder
     */
    class ViewHolder(parent: ViewGroup?, layoutId: Int) : RecyclerViewHolder(parent!!, layoutId) {
        var mHeadName: TextView
        var mHeadImg: ImageView
        var mIntimateValue: TextView

        init {
            mHeadName = itemView.findViewById(R.id.tv_follow_name)
            mHeadImg = itemView.findViewById(R.id.iv_follow_head)
            mIntimateValue = itemView.findViewById(R.id.tv_follow_sign)
        }
    }
}