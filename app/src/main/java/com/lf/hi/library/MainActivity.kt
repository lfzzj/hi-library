package com.lf.hi.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lf.hi.library.demo.log.HiLogDemoActivity
import com.lf.hi.library.demo.tab.HiTabBottomDemoActivity

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>( R.id.btn_log).setOnClickListener(this)
        findViewById<View>( R.id.btn_bottom).setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_log->{
                startActivity(Intent(this,
                    HiLogDemoActivity::class.java))
            }
            R.id.btn_bottom->{
                startActivity(Intent(this,HiTabBottomDemoActivity::class.java))
            }
        }
    }
}