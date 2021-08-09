package com.lf.common.ui.component

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lf.common.R
import com.lf.common.ui.view.EmptyView
import com.lf.common.ui.view.HiRecyclerView
import com.lf.hi.ui.dataitem.HiAdapter
import com.lf.hi.ui.dataitem.HiDataItem
import com.lf.hi.ui.refresh.HiOverView
import com.lf.hi.ui.refresh.HiRefresh
import com.lf.hi.ui.refresh.HiRefreshLayout
import com.lf.hi.ui.refresh.HiTextOverView
import kotlinx.android.synthetic.main.fragment_list.*

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.common.ui.component$
 * @ClassName:      HiAbsListFragment$
 * @Author:         LF
 * @CreateDate:     2021/8/6$ 15:51$
 * @Description:
 */
open class HiAbsListFragment : HiBaseFragment(), HiRefresh.HiRefreshListener {
    private var pageIndex: Int = 1
    private lateinit var hiAdapter: HiAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var refreshHeaderView: HiTextOverView
    private var loadingView: ContentLoadingProgressBar? = null
    private var emptyView: EmptyView? = null
    private var recyclerView: HiRecyclerView? = null
    private var refreshLayout: HiRefreshLayout? = null

    companion object {
        const val PREFETCH_SIZE = 5
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.refreshLayout = refresh_layout
        this.recyclerView = recycler_view
        this.emptyView = empty_view
        this.loadingView = content_loading

        refreshHeaderView = HiTextOverView(context!!)
        refreshLayout?.setRefreshOverView(refreshHeaderView)
        refreshLayout?.setHiRefreshListener(this)

        layoutManager = createLayoutManager()
        hiAdapter = HiAdapter(context!!)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = hiAdapter

        emptyView?.visibility = View.GONE
        emptyView?.setIcon(R.string.list_empty)
        emptyView?.setDesc(getString(R.string.list_empty_desc))
        emptyView?.setButton(getString(R.string.list_empty_action), View.OnClickListener {
            onRefresh()
        })

        loadingView?.visibility = View.VISIBLE
        pageIndex = 1
    }

    fun finishRefresh(dataItems: List<HiDataItem<*, RecyclerView.ViewHolder>>?) {
        val success = dataItems != null && dataItems.isNotEmpty()
        //
        val refresh = pageIndex == 1
        if (refresh) {
            loadingView?.visibility = View.GONE
            refreshLayout?.refreshFinished()
            if (success) {
                hiAdapter.clearItems()
                hiAdapter.addItems(dataItems!!, true)
            } else {
                //判断列表上是否已经有数据，如果没有，显示空界面
                if (hiAdapter.itemCount <= 0) {
                    emptyView?.visibility = View.VISIBLE
                }
            }
        } else {
            if (success) {
                hiAdapter.addItems(dataItems!!, true)
            }
            recyclerView?.loadFinished(success)
        }
    }

    fun enableLoadMore(callback: () -> Unit) {
        recyclerView?.enableLoadMore({
            if (refreshHeaderView.state == HiOverView.HiRefreshState.STATE_REFRESH) {
                //正处于刷新状态
                recyclerView?.loadFinished(false)
                return@enableLoadMore
            }
            pageIndex++
            callback()
        }, PREFETCH_SIZE)
    }

    fun disableLoadMore() {
        recyclerView?.disableLoadMore()
    }


    private fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun enableRefresh(): Boolean {
        return true
    }

    @CallSuper //CallSuper->不能被子类复写逻辑
    override fun onRefresh() {
        if (recyclerView?.isLoading() == true) {
            //正处于分页
            refreshLayout?.post {
                refreshLayout?.refreshFinished()
            }

        }
        pageIndex = 1
    }
}