package com.lf.common.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lf.common.R
import com.lf.hi.hilibrary.log.HiLog
import com.lf.hi.ui.dataitem.HiAdapter

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.common.ui.view$
 * @ClassName:      HiRecyclerView$
 * @Author:         LF
 * @CreateDate:     2021/8/6$ 15:01$
 * @Description:
 */
class HiRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var loadMoreScrollerListener: OnScrollListener? = null
    private var footerView: View? = null
    private var isLoadingMore: Boolean = false

    inner class LoadMoreScrollerListener(val prefetchSize: Int, val callback: () -> Unit) :
        OnScrollListener() {
        //强转成HiAdapter，会有前置检查
        val hiAdapter = adapter as HiAdapter
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            //需要根据当前的滑动状态  已决定要不要添加footerview 要不要执行上拉加载分页的动作
            if (isLoadingMore) {
                return
            }
            //需要判断当前表类上 已经显示的item的个数，如果列表上已显示的数据小于0
            val totalItemCount = hiAdapter.itemCount
            if (totalItemCount <= 0) {
                return
            }
            //需要在滑动状态为拖动状态时，就要判断要不要添加footer
            //不能在 滑动停止了 才去添加footer  newState ==  SCROLL_STATE_IDLE
            //防止列表滑动到了底部了，但是footerview还没显示出来
            //1.需要判断列表是否能够滑动
            val canScrollVertical = recyclerView.canScrollVertically(1)
            //特殊情况 列表已经滑动到底部了，但是分页失败了
            val lastVisibleItem = findLastVisibleItem(recyclerView)
            if (lastVisibleItem <= 0) {
                return
            }
            val arriveBottom = lastVisibleItem >= totalItemCount - 1
            //可以向下滑动，或者当前以及滑动到最底下了，此时再滑动列表，也是运行分页的
            if (newState == SCROLL_STATE_DRAGGING && (canScrollVertical || arriveBottom)) {
                addFooterView()
            }
            if (newState == SCROLL_STATE_IDLE) {
                return
            }
            //预加载  不需要滑动到最后一个item的时候 就触发下一页的加载动作
            val arrivePrefetchPosition = totalItemCount - lastVisibleItem <= prefetchSize
            if (!arrivePrefetchPosition) {
                return
            }
            isLoadingMore = true
            callback()
        }

        private fun addFooterView() {
            val footerView = getFooterView()
            //在一些边界场景下，会出现多次添加的情况 添加之前先remove
            //主要是为了避免removeFooterView 不及时，在边界场景中可能会出现footerView还没从recyclerview移除掉
            //但是我们又调用了addFooterView,造成的重复添加的情况，
            // 此时会抛出add view must call removeview form it parent first exception
            if (footerView.parent != null) {
                footerView.post {
                    addFooterView()
                }
            }
            hiAdapter.addFooterView(footerView)
        }

        private fun getFooterView(): View {
            if (footerView == null) {
                footerView = LayoutInflater.from(context)
                    .inflate(R.layout.layout_footer_loading, this@HiRecyclerView, false)
            }
            return footerView!!
        }

        private fun findLastVisibleItem(recyclerView: RecyclerView): Int {
            when (val layoutManager = recyclerView.layoutManager) {
                is LinearLayoutManager -> {
                    return layoutManager.findLastVisibleItemPosition()
                }
                is StaggeredGridLayoutManager -> {
                    return layoutManager.findLastVisibleItemPositions(null)[0]
                }
            }
            return -1
        }
    }

    fun enableLoadMore(callback: () -> Unit, prefetchSize: Int) {
        if (adapter !is HiAdapter) {
            HiLog.e("enableLoadMore must use HiAdapter")
            return
        }
        loadMoreScrollerListener = LoadMoreScrollerListener(prefetchSize, callback)
        addOnScrollListener(loadMoreScrollerListener!!)
    }

    fun disableLoadMore(){
        if (adapter !is HiAdapter) {
            HiLog.e("disableLoadMore must use HiAdapter")
            return
        }
        val hiAdapter = adapter as HiAdapter
        footerView?.let {
            if (footerView!!.parent !=null){
                hiAdapter.removeFooterView(footerView!!)
            }
        }
        loadMoreScrollerListener?.let {
            removeOnScrollListener(loadMoreScrollerListener!!)
            loadMoreScrollerListener = null
            footerView = null
            isLoadingMore = false
        }
    }
    fun isLoading():Boolean {
        return isLoadingMore
    }
    fun loadFinished(success:Boolean){
        if (adapter !is HiAdapter) {
            HiLog.e("loadFinished must use HiAdapter")
            return
        }
        isLoadingMore = false
        val hiAdapter =  adapter as HiAdapter
        if (!success){
            footerView?.let {
                if (footerView!!.parent !=null){
                    hiAdapter.removeFooterView(footerView!!)
                }
            }
        }else{

        }
    }
}