package com.lf.common.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.common.utils$
 * @ClassName:      SPUtil$
 * @Author:         LF
 * @CreateDate:     2021/8/2$ 10:46$
 * @Description:
 */

object SPUtil {
    val CACHE_FILE = "cache_file"

    fun getString(key: String): String? {
        val sharad = getShared()
        if (sharad != null) {
            return sharad.getString(key, null)
        }
        return null
    }

    fun getBoolean(key: String): Boolean? {
        val sharad = getShared()
        if (sharad != null) {
            return sharad.getBoolean(key, false)
        }
        return false
    }

    fun getInt(key: String): Int? {
        val sharad = getShared()
        if (sharad != null) {
            return sharad.getInt(key, 0)
        }
        return 0
    }

    fun putString(key: String, value: String) {
        val shared = getShared()
        if (shared != null) {
            shared.edit().putString(key, value).commit()
        }
    }

    fun putBoolean(key: String, value: Boolean) {
        val shared = getShared()
        if (shared != null) {
            shared.edit().putBoolean(key, value).commit()
        }
    }

    fun putInt(key: String, value: Int) {
        val shared = getShared()
        if (shared != null) {
            shared.edit().putInt(key, value).commit()
        }
    }

    fun getShared(): SharedPreferences? {
        val application: Application? = AppGlobals.application
        if (application != null) {
            return application.getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE)
        }
        return null
    }
}