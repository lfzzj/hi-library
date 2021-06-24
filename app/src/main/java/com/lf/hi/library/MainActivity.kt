package com.lf.hi.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lf.hi.library.demo.log.HiLogDemoActivity
import com.lf.hi.library.demo.tab.HiTabBottomDemoActivity
import com.lf.hi.ui.tab.bottom.HiTabBottom
import com.lf.hi.ui.tab.bottom.HiTabBottomInfo

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn_log).setOnClickListener(this)
        findViewById<View>(R.id.btn_bottom).setOnClickListener(this)
        val tabBottom: HiTabBottom = findViewById<HiTabBottom>(R.id.tab_bottom)
        val homeInfo = HiTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        tabBottom.hiTabInfo = homeInfo
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_log -> {
                startActivity(Intent(this, HiLogDemoActivity::class.java))
            }
            R.id.btn_bottom -> {
                startActivity(Intent(this, HiTabBottomDemoActivity::class.java))
            }
        }
    }
}