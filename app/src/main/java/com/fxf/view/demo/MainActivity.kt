package com.fxf.view.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.fxf.stickheader.BaseStickRecyclerAdapter
import com.jcodecraeer.xrecyclerview.XRecyclerView
import java.util.*

/**
 *
 */
class MainActivity : AppCompatActivity() {
    private var mRecyclerView: XRecyclerView? = null
    private var mAdapter: BaseStickRecyclerAdapter<*, *>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.rv_list) as XRecyclerView?
        val datasList: MutableList<FansMemberBean?> = ArrayList()
        var index = 0
        for (i in 0..2) {
            val bean = FansMemberBean( "粉丝团团长")
            bean.name = bean.groupName
            bean.intimateValue = "亲密值 ${index++}"
            bean.fansName = "李家军"
            bean.fansCount = 0
            datasList.add(bean)
        }
        for (i in 0..7) {
            val bean = FansMemberBean( "成员")
            bean.name = bean.groupName
            bean.intimateValue = "亲密值 ${index++}"
            bean.fansName = "齐家军"
            bean.fansCount = 4
            datasList.add(bean)
        }
        for (i in 0..2) {
            val bean = FansMemberBean( "粉丝团团长")
            bean.name = bean.groupName
            bean.intimateValue = "亲密值 ${index++}"
            bean.fansName = "张家军"
            bean.fansCount = 6
            datasList.add(bean)
        }
        for (i in 0..2) {
            val bean = FansMemberBean( "成员")
            bean.name = bean.groupName
            bean.intimateValue = "亲密值 ${index++}"
            bean.fansName = "周家军"
            bean.fansCount = 8
            datasList.add(bean)
        }
        for (i in 0..7) {
            val bean = FansMemberBean( "粉丝团团长")
            bean.name = bean.groupName
            bean.intimateValue = "亲密值 ${index++}"
            bean.fansName = "齐家军"
            bean.fansCount = 4
            datasList.add(bean)
        }
        mAdapter = FansRecyclerAdapter(this, datasList)
        mRecyclerView!!.addItemDecoration(FansStickDecoration(this))
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        val header = LayoutInflater.from(this).inflate(R.layout.header_fans_list, mRecyclerView,false)
        mRecyclerView!!.addHeaderView(header)
        mRecyclerView!!.adapter = mAdapter
    }
}