package com.lf.hi.library.biz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lf.common.ui.component.HiBaseActivity
import com.lf.hi.hilibrary.restful.HiCallback
import com.lf.hi.hilibrary.restful.HiResponse
import com.lf.hi.library.R
import com.lf.hi.library.http.ApiFactory
import com.lf.hi.library.http.api.AccountApi
import kotlinx.android.synthetic.main.activity_registration.*

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.biz$
 * @ClassName:      RegistrationActivity$
 * @Author:         LF
 * @CreateDate:     2021/8/2$ 15:43$
 * @Description:
 */
@Route(path = "/account/registration")
class RegistrationActivity:HiBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        action_back.setOnClickListener { onBackPressed() }

        action_submit.setOnClickListener { submit() }
    }

    private fun submit() {
        val username = input_item_username.getEditText().text.toString()
        val pwd = input_item_pwd.getEditText().text.toString()
        val pwdSec = input_item_pwd_check.getEditText().text.toString()

        ApiFactory.create(AccountApi::class.java).register(username,pwd).enqueue(object :HiCallback<String>{
            override fun onSuccess(response: HiResponse<String>) {
                if (response.code == HiResponse.SUCCESS){
                    //注册成功
                    var intent =Intent()
                    intent.putExtra("username",username)
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }
            }

            override fun onFailed(throwable: Throwable) {

            }
        })
    }
}