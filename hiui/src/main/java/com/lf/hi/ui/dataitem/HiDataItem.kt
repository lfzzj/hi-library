package com.lf.hi.ui.dataitem

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.ui.dataitem$
 * @ClassName:      HiDataItem$
 * @Author:         LF
 * @CreateDate:     2021/7/6$ 11:40$
 * @Description:
 */
abstract class HiDataItem<DATA, VH : RecyclerView.ViewHolder>(data: DATA) {
    private lateinit var adapter: HiAdapter
    var mData: DATA? = null

    init {
        this.mData = data
    }

    abstract fun onBindData(holder: RecyclerView.ViewHolder, position: Int)

    /**
     * 返回该item的布局资源id
     */
    open fun getItemLayoutRes(): Int {
        return -1
    }

    /**
     * 返回该item的视图view
     */
    open fun getItemView(parent: ViewGroup): View? {
        return null
    }

    fun setAdapter(adapter: HiAdapter) {
        this.adapter = adapter
    }

    /**
     * 刷新列表
     */
    fun refreshItem() {
        adapter.refreshItem(this)
    }

    /**
     * 从列表中移除
     */
    fun removeItem() {
        adapter.removeItem(this)
    }

    /**
     * 该方法在列表上占据几列
     */
    fun getSpanSize(): Int {
        return 0
    }

    fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder){
        adapter.onViewDetachedFromWindow(holder)
    }
}