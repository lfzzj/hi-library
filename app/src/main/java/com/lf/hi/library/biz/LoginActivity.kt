package com.lf.hi.library.biz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lf.common.ui.component.HiBaseActivity
import com.lf.common.utils.SPUtil
import com.lf.hi.hilibrary.log.HiLog
import com.lf.hi.hilibrary.restful.HiCallback
import com.lf.hi.hilibrary.restful.HiResponse
import com.lf.hi.library.R
import com.lf.hi.library.http.ApiFactory
import com.lf.hi.library.http.api.AccountApi
import kotlinx.android.synthetic.main.activity_login.*

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.biz$
 * @ClassName:      LoginActivity$
 * @Author:         LF
 * @CreateDate:     2021/7/30$ 11:19$
 * @Description:
 */
@Route(path = "/account/login")
class LoginActivity : HiBaseActivity() {
    private val REQUEST_CODE_REGISTRATION: Int = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        action_back.setOnClickListener {
            onBackPressed()
        }
        action_register.setOnClickListener {
            goRegisteration()
        }
        action_login.setOnClickListener {
            goLogin()
        }
    }

    private fun goLogin() {
        val name = input_item_username.getEditText().text
        val password = input_item_password.getEditText().text


        ApiFactory.create(AccountApi::class.java).login(name.toString(), password.toString())
            .enqueue(object : HiCallback<String> {
                override fun onSuccess(response: HiResponse<String>) {
                    if (response.code == HiResponse.SUCCESS) {
                        HiLog.dt("zzj", "登录成功 -->${response.data}")
                        val data = response.data
                        SPUtil.putString("boarding-pass", data!!)
                        setResult(Activity.RESULT_OK, Intent())
                        finish()
                    } else {
                        HiLog.dt("zzj", "登录失败 -->${response.msg}")
                    }

                }

                override fun onFailed(throwable: Throwable) {
                    HiLog.dt("zzj", "登录失败 -->${throwable.message}")
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((resultCode == Activity.RESULT_OK) and (data != null) and (requestCode == REQUEST_CODE_REGISTRATION)) {
            val username = data!!.getStringExtra("username")
            if (!TextUtils.isEmpty(username)) {
                input_item_username.getEditText().setText(username)
            }
        }
    }

    private fun goRegisteration() {
        ARouter.getInstance().build("/account/registration")
            .navigation(this, REQUEST_CODE_REGISTRATION)
    }
}