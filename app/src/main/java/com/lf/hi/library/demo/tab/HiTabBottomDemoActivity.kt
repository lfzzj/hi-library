package com.lf.hi.library.demo.tab

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lf.hi.hilibrary.util.HiDisplayUtil
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
        val infoHome = HiTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val infoRecommend = HiTabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            "#ff656667",
            "#ffd44949"
        )

//        val infoCategory = HiTabBottomInfo(
//            "分类",
//            "fonts/iconfont.ttf",
//            getString(R.string.if_classic),
//            null,
//            "#ff656667",
//            "#ffd44949"
//        )
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_jetpack, null)
        val infoCategory = HiTabBottomInfo<String> (
            "分类",
            bitmap,
            bitmap
        )

        val infoChat = HiTabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_cart),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val infoMine = HiTabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_mine),
            null,
            "#ff656667",
            "#ffd44949"
        )

        bottomInfoList.add(infoHome)
        bottomInfoList.add(infoRecommend)
        bottomInfoList.add(infoCategory)
        bottomInfoList.add(infoChat)
        bottomInfoList.add(infoMine)
        hiTabBottomLayout.inflateInfo(bottomInfoList)
        hiTabBottomLayout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this@HiTabBottomDemoActivity, nextInfo!!.name, Toast.LENGTH_SHORT).show()
        }
        //设置默认选中
        hiTabBottomLayout.defaultSelected(infoHome)
        val tabBottom = hiTabBottomLayout.findTab(bottomInfoList[2])
        tabBottom?.apply {
            resetHeight(HiDisplayUtil.dp2px(context,66f))
        }
    }
}