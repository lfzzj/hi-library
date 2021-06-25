package com.lf.hi.library.demo.refresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.lf.hi.library.R
import com.lf.hi.ui.refresh.HiRefresh
import com.lf.hi.ui.refresh.HiRefreshLayout
import com.lf.hi.ui.refresh.HiTextOverView

class HiRefreshDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_refresh_demo)
        val refreshLayout = findViewById<HiRefreshLayout>(R.id.refresh_layout)
        val xOverView = HiTextOverView(this)
        refreshLayout.setRefreshOverView(xOverView)
        refreshLayout.setHiRefreshListener(object :HiRefresh.HiRefreshListener{
            override fun enableRefresh(): Boolean {
                return true
            }

            override fun onRefresh() {
                Handler().postDelayed({
                    refreshLayout.refreshFinished()
                },1000)
            }
        })
    }
}