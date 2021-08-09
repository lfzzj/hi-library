package com.lf.hi.library

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import androidx.lifecycle.whenStarted
import com.lf.common.ui.component.HiBaseActivity
import com.lf.hi.hilibrary.log.HiLog
import com.lf.hi.hilibrary.restful.HiCallback
import com.lf.hi.hilibrary.restful.HiResponse
import com.lf.hi.library.demo.banner.HiBannerDemoActivity
import com.lf.hi.library.demo.coroutine.CoroutineScene3
import com.lf.hi.library.demo.log.HiLogDemoActivity
import com.lf.hi.library.demo.refresh.HiRefreshDemoActivity
import com.lf.hi.library.demo.tab.HiTabBottomDemoActivity
import com.lf.hi.library.demo.tab.HiTabTopDemoActivity
import com.lf.hi.library.http.ApiFactory
import com.lf.hi.library.http.api.AccountApi
import kotlinx.coroutines.launch
import org.json.JSONObject

class DemoActivity : HiBaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        findViewById<View>(R.id.btn_log).setOnClickListener(this)
        findViewById<View>(R.id.btn_bottom).setOnClickListener(this)
        findViewById<View>(R.id.btn_top).setOnClickListener(this)
        findViewById<View>(R.id.btn_refresh).setOnClickListener(this)
        findViewById<View>(R.id.btn_banner).setOnClickListener(this)
        findViewById<View>(R.id.btn_coroutine).setOnClickListener(this)

        ApiFactory.create(AccountApi::class.java).listCities("").enqueue(object : HiCallback<JSONObject> {
            override fun onSuccess(response: HiResponse<JSONObject>) {
                HiLog.dt("zzj", "--------@{response}" )
            }

            override fun onFailed(throwable: Throwable) {
            }
        })


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
            R.id.btn_coroutine -> {
//                CoroutineScene.startScene2()
                lifecycleScope.launch { //用lifecycleScope启动协程 避免内存泄漏
                    val content = CoroutineScene3.parseAssetsFile(assets, "config.json")
                }

                lifecycleScope.launchWhenCreated {
                    //是指当我们的宿主的生命周期，至少为oncreate的时候才会启动
                    whenCreated {//
                        //这里的代码只有当 宿主的生命周期为oncreate才会执行，否则都是暂停
                    }
                    whenResumed {
                        //这里的代码只有当 宿主的生命周期为onResume才会执行，否则都是暂停
                    }
                    whenStarted {
                        //这里的代码只有当 宿主的生命周期为onStart才会执行，否则都是暂停
                    }
                }
                lifecycleScope.launchWhenStarted {
                    //是指当我们的宿主的生命周期，至少为onStart的时候才会启动
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val view = findViewById<View>(R.id.btn_log)
        Log.e("TAG", "onResume: width: ${view.width} height: ${view.height}")

        view.post {
            Log.e("TAG", "onResume:post,width: ${view.width} height: ${view.height}")
        }

        view.viewTreeObserver.addOnGlobalLayoutListener {
            view.viewTreeObserver.removeOnGlobalLayoutListener { this }
            Log.e("TAG", "onResume:viewTreeObserver width: ${view.width} height: ${view.height}")
        }
    }
}