package com.lf.hi.library

import android.app.Application
import com.google.gson.Gson
import com.lf.common.ui.component.HiBaseApplication
import com.lf.hi.hilibrary.log.HiConsolePrinter
import com.lf.hi.hilibrary.log.HiLogConfig
import com.lf.hi.hilibrary.log.HiLogManager
import com.lf.hi.hilibrary.log.HiLogPrinter

/**
@author: LF
@data on 2021/4/14 下午10:36
@desc TODO
 */
class HiApplication : HiBaseApplication() {
    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParser(): jsonParser {
                return jsonParser { src -> Gson().toJson(src) }
            }

            override fun getGlobalTag(): String {
                return "MApplication"
            }

            override fun enable(): Boolean {
                return true
            }
        }, HiConsolePrinter())

    }
}