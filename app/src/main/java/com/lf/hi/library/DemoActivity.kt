package com.lf.hi.library

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lf.common.ui.component.HiBaseActivity
import com.lf.hi.library.demo.banner.HiBannerDemoActivity
import com.lf.hi.library.demo.log.HiLogDemoActivity
import com.lf.hi.library.demo.refresh.HiRefreshDemoActivity
import com.lf.hi.library.demo.tab.HiTabBottomDemoActivity
import com.lf.hi.library.demo.tab.HiTabTopDemoActivity
import com.lf.hi.ui.refresh.HiRefresh
import com.lf.hi.ui.tab.bottom.HiTabBottom
import com.lf.hi.ui.tab.bottom.HiTabBottomInfo

class DemoActivity : HiBaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        findViewById<View>(R.id.btn_log).setOnClickListener(this)
        findViewById<View>(R.id.btn_bottom).setOnClickListener(this)
        findViewById<View>(R.id.btn_top).setOnClickListener(this)
        findViewById<View>(R.id.btn_refresh).setOnClickListener(this)
        findViewById<View>(R.id.btn_banner).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_log -> {
                startActivity(Intent(this, HiLogDemoActivity::class.java))
            }
            R.id.btn_bottom -> {
                startActivity(Intent(this, HiTabBottomDemoActivity::class.java))
            }
            R.id.btn_top -> {
                startActivity(Intent(this, HiTabTopDemoActivity::class.java))
            }
            R.id.btn_refresh -> {
                startActivity(Intent(this, HiRefreshDemoActivity::class.java))
            }
            R.id.btn_banner -> {
                startActivity(Intent(this, HiBannerDemoActivity::class.java))
            }
        }
    }
}