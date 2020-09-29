package com.fxf.view.demo

import android.content.Context
import android.graphics.*
import com.fxf.stickheader.BaseStickDecoration

class FansStickDecoration(context: Context) : BaseStickDecoration<FansRecyclerAdapter>(context) {


    /**
     * 预先获得头部内一个图片，以计算高度
     */
    override val bitmap: Bitmap
        get() = iconMap.values.first()

    /**
     * 这里添加头名称和对应icon
     */
    override fun initIconGroups() {
        iconMap["粉丝团团长"] = BitmapFactory.decodeResource(mContext.resources, R.mipmap.ic_group_leader)
        iconMap["成员"] = BitmapFactory.decodeResource(mContext.resources, R.mipmap.ic_group_fans)
    }


}