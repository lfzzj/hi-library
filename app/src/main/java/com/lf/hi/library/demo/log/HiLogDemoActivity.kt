package com.lf.hi.library.demo.log

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lf.hi.hilibrary.log.*
import com.lf.hi.library.R

/**
@author: LF
@data on 2021/4/14 下午10:30
@desc TODO
 */
class HiLogDemoActivity : AppCompatActivity() {
    var viewParent :HiViewPrinter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_log_demo)
        viewParent = HiViewPrinter(this)
        findViewById<View>(R.id.btn_log).setOnClickListener {
            printLog()
        }
        viewParent!!.viewProvider.showFloatingView()

    }

    private fun printLog() {
        HiLogManager.getInstance().addPrinter(viewParent)
        HiLog.a("1111111")
        //自定义log配置
        HiLog.log(object : HiLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, HiLogType.E, "------", "5566")
    }
}