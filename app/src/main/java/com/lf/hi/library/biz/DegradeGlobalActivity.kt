package com.lf.hi.library.biz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.lf.common.ui.component.HiBaseActivity
import com.lf.hi.library.R
import kotlinx.android.synthetic.main.layout_global_degrade.*

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.biz$
 * @ClassName:      DegradeGlobalActivity$
 * @Author:         LF
 * @CreateDate:     2021/8/3$ 16:17$
 * @Description:全局统一错误页
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : HiBaseActivity() {
    @JvmField
    @Autowired
    var degrade_title: String? = null
    @JvmField
    @Autowired
    var degrade_desc: String? = null
    @JvmField
    @Autowired
    var degrade_action: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_global_degrade)

        empty_view.setIcon(R.string.if_detail)
        if (degrade_title != null) {
            empty_view.setTitle(degrade_title!!)
        }
        if (degrade_desc != null) {
            empty_view.setDesc(degrade_desc!!)
        }
        if (degrade_action != null) {
            empty_view.setHelpAction(listener = View.OnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(degrade_action))
                startActivity(intent)
            })
        }
        action_back.setOnClickListener { onBackPressed() }
    }
}