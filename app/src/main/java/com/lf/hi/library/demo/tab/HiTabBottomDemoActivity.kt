package com.lf.hi.library.demo.tab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lf.hi.library.R
import com.lf.hi.ui.tab.bottom.HiTabBottomInfo
import com.lf.hi.ui.tab.bottom.HiTabBottomLayout

/**
@author: LF
@data on 2021/6/7 下午10:42
@desc TODO
 */
class HiTabBottomDemoActivity : AppCompatActivity() {
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_bottom_demo)

        initTabBottom()
    }

    private fun initTabBottom() {
        val hiTabBottomLayout: HiTabBottomLayout = findViewById(R.id.hi_tab_layout)
        hiTabBottomLayout.setTabAlpha(0.85f)
        val bottomInfoList: MutableList<HiTabBottomInfo<*>> = ArrayList()
        val homeInfo = HiTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val recommendInfo = HiTabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_classic),
            null,
            "#ff656667",
            "#ffd44949"
        )

        bottomInfoList.add(homeInfo)
        bottomInfoList.add(recommendInfo)
        hiTabBottomLayout.inflateInfo(bottomInfoList)
        hiTabBottomLayout.addTabSelectedChangeListener { _, _, nextInfo ->

        }
        hiTabBottomLayout.defaultSelected(homeInfo)


    }
}