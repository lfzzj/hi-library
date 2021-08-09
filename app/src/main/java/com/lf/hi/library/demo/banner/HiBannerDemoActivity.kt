package com.lf.hi.library.demo.banner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.lf.common.ui.view.loadUrl
import com.lf.hi.library.R
import com.lf.hi.ui.banner.HiBanner
import com.lf.hi.ui.banner.core.HiBannerMo
import com.lf.hi.ui.banner.indicator.HiCircleIndicator
import com.lf.hi.ui.banner.indicator.HiIndicator

class HiBannerDemoActivity : AppCompatActivity() {
    private var urls = arrayOf(
        "http://gank.io/images/f4f6d68bf30147e1bdd4ddbc6ad7c2a2",
        "http://gank.io/images/dc75cbde1d98448183e2f9514b4d1320",
        "http://gank.io/images/6b2efa591564475fb8bc32291fb0007c",
        "http://gank.io/images/d6bba8cf5b8c40f9ad229844475e9149",
        "http://gank.io/images/9fa43020cf724c69842eec3e13f6d21c",
        "http://gank.io/images/d237f507bf1946d2b0976e581f8aab9b",
        "http://gank.io/images/25d3e3db2c1248bb917c09dc4f50a46f",
        "http://gank.io/images/19c99c447e0a40a6b3ff89951957cfb1"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_banner_demo_main)
        initView(HiCircleIndicator(this), false)
    }

    private fun initView(hiIndicator: HiIndicator<*>, autoPlay: Boolean) {
        val mHiBanner = findViewById<HiBanner>(R.id.banner)
        val moList: MutableList<HiBannerMo> = ArrayList()
        for (i in 0..7) {
            val mo = BannerMo()
            mo.url = urls[i % urls.size]
            moList.add(mo)
        }
        mHiBanner.setHiIndicator(hiIndicator)
        mHiBanner.setAutoPlay(true)
        mHiBanner.setIntervalTime(2000)
        mHiBanner.setBannerData(R.layout.banner_item_layout, moList)
        mHiBanner.setBindAdapter { viewHolder, mo, position ->
            val imageView = viewHolder.findViewById<ImageView>(R.id.iv_image)
            imageView.loadUrl(mo.url)
            val titleView: TextView = viewHolder.findViewById(R.id.tv_title)
            titleView.text = mo.url
        }
    }
}