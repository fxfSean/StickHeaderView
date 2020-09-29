package com.fxf.stickheader
import com.fxf.stickheader.BaseStickDecoration
/**
 *
 * 要实现分组列表的实体类base父类
 * 实体类要继承此bean，并设置组头 groupName
 * 参考demo中 FansMemberBean
 */
public open class BaseStickBean(var groupName: String)