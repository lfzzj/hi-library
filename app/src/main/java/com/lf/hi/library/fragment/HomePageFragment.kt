package com.lf.hi.library.fragment

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.lf.common.ui.component.HiBaseFragment
import com.lf.hi.hilibrary.restful.HiCallback
import com.lf.hi.hilibrary.restful.HiResponse
import com.lf.hi.library.R
import com.lf.hi.library.http.ApiFactory
import com.lf.hi.library.http.api.HomeApi
import com.lf.hi.library.model.TabCategory
import com.lf.hi.ui.tab.bottom.HiTabBottomInfo
import com.lf.hi.ui.tab.top.HiTabTop
import com.lf.hi.ui.tab.top.HiTabTopInfo
import kotlinx.android.synthetic.main.fragment_home.*

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.fragment$
 * @ClassName:      HomePageFragment$
 * @Author:         LF
 * @CreateDate:     2021/8/9$ 14:13$
 * @Description:
 */
class HomePageFragment : HiBaseFragment() {
    private var topTabSelectIndex: Int = 0
    private val selectTabIndex: Int = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        queryTabList()
    }

    private fun queryTabList() {
        ApiFactory.create(HomeApi::class.java).queryTabList()
            .enqueue(object : HiCallback<List<TabCategory>> {
                override fun onSuccess(response: HiResponse<List<TabCategory>>) {
                    val data = response.data
                    if (response.successful() && data != null) {
                        updataUI(data!!)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                }
            })
    }

    private fun updataUI(data: List<TabCategory>) {
        if (!isAlive()) return
        val toptabLayout = top_tab_layout
        val viewPager = view_pager

        val topTabs = mutableListOf<HiTabTopInfo<Int>>()
        data.forEachIndexed { index, tabCategory ->
            val defaultColor = ContextCompat.getColor(context!!, R.color.color_333)
            val selectColor = ContextCompat.getColor(context!!, R.color.color_dd2)
            val tabTopInfo = HiTabTopInfo<Int>(tabCategory.categoryName, defaultColor, selectColor)
            topTabs.add(tabTopInfo)
        }
        toptabLayout.inflateInfo(topTabs as List<HiTabTopInfo<*>>)
        toptabLayout.defaultSelected(topTabs[selectTabIndex])
        toptabLayout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
            //点击之后选中的那个下标
            if (viewPager.currentItem != index) {
                viewPager.setCurrentItem(index, false)
            }
        }
        viewPager.adapter = HomePagerAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            data
        )
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                //这个方法被触发有两种可能，第一种切换顶部tab 第二种 手动滑动翻页
                //如果是手动滑动翻页

                if (position != topTabSelectIndex) {
                    //去通知toptabLayout进行切换
                    toptabLayout.defaultSelected(topTabs[position])
                    topTabSelectIndex = position
                }
            }
        })
    }

    inner class HomePagerAdapter(fm: FragmentManager, behavior: Int, val tabs: List<TabCategory>) :
        FragmentPagerAdapter(fm, behavior) {
        val fragments = SparseArray<Fragment>(tabs.size)

        override fun getItem(position: Int): Fragment {
            var fragment = fragments.get(position, null)
            if (fragment == null) {
                fragment = HomeTabFragment.newInstance(tabs[position].categoryId)
                fragments.put(position, fragment)
            }
            return fragment
        }

        override fun getCount(): Int {
            return fragments.size()
        }

    }
}