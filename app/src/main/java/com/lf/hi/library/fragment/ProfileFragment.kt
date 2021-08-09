package com.lf.hi.library.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.lf.common.ui.component.HiBaseFragment
import com.lf.common.ui.view.loadCircle
import com.lf.common.ui.view.loadCorner
import com.lf.common.ui.view.loadUrl
import com.lf.hi.hilibrary.restful.HiCallback
import com.lf.hi.hilibrary.restful.HiResponse
import com.lf.hi.hilibrary.util.HiDisplayUtil
import com.lf.hi.library.R
import com.lf.hi.library.demo.banner.BannerMo
import com.lf.hi.library.http.ApiFactory
import com.lf.hi.library.http.api.AccountApi
import com.lf.hi.library.model.CourseNotice
import com.lf.hi.library.model.Notice
import com.lf.hi.library.model.UserProfile
import com.lf.hi.ui.banner.core.HiBannerMo
import com.lf.hi.ui.banner.indicator.HiCircleIndicator
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.fragment$
 * @ClassName:      ProfileFragment$
 * @Author:         LF
 * @CreateDate:     2021/8/5$ 15:30$
 * @Description:
 */
class ProfileFragment : HiBaseFragment() {
    private val REQUEST_CODE_LOGIN_PROFILE: Int = 1001
    val ITEM_PLACE_HOLDER = "  "
    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        item_course.setText(R.string.if_favorite)
        item_course.append(ITEM_PLACE_HOLDER + getString(R.string.item_notify))
        notify_count.visibility = View.VISIBLE

        item_collection.setText(R.string.if_favorite)
        item_collection.append(ITEM_PLACE_HOLDER + getString(R.string.item_notify))

        item_address.setText(R.string.if_favorite)
        item_address.append(ITEM_PLACE_HOLDER + getString(R.string.item_notify))

        item_history.setText(R.string.if_favorite)
        item_history.append(ITEM_PLACE_HOLDER + getString(R.string.item_notify))

        queryLoginUserData()
        queryCourseNotice()

        test()
    }

    private var urls = arrayOf(
        "http://gank.io/images/f4f6d68bf30147e1bdd4ddbc6ad7c2a2",
        "http://gank.io/images/dc75cbde1d98448183e2f9514b4d1320",
        "http://gank.io/images/6b2efa591564475fb8bc32291fb0007c",
        "http://gank.io/images/d6bba8cf5b8c40f9ad229844475e9149",
        "http://gank.io/images/9fa43020cf724c69842eec3e13f6d21c",
        "http://gank.io/images/d237f507bf1946d2b0976e581f8aab9b",
        "http://gank.io/images/25d3e3db2c1248bb917c09dc4f50a46f",
        "http://gank.io/images/19c99c447e0a40a6b3ff89951957cfb1"
    )

    private fun test() {
        tab_item_collection.text =
            spannableTabItem(20, getString(R.string.profile_tab_item_collection))
        tab_item_history.text = spannableTabItem(1000, getString(R.string.profile_tab_item_history))
        tab_item_learn.text = spannableTabItem(99, getString(R.string.profile_tab_item_learn))

        //
        val moList: MutableList<HiBannerMo> = ArrayList()
        for (i in 0..7) {
            val mo = BannerMo()
            mo.url = urls[i % urls.size]
            moList.add(mo)
        }
        hi_banner.visibility = View.VISIBLE
        hi_banner.setHiIndicator(HiCircleIndicator(activity!!))
        hi_banner.setAutoPlay(true)
        hi_banner.setIntervalTime(2000)
        hi_banner.setBannerData(R.layout.banner_item_layout, moList)
        hi_banner.setBindAdapter { viewHolder, mo, position ->
            val imageView = viewHolder.findViewById<ImageView>(R.id.iv_image)
            imageView.loadUrl(mo.url)
            val titleView: TextView = viewHolder.findViewById(R.id.tv_title)
            titleView.text = mo.url
        }
    }

    private fun queryLoginUserData() {
        ApiFactory.create(AccountApi::class.java).profile()
            .enqueue(object : HiCallback<UserProfile> {
                override fun onSuccess(response: HiResponse<UserProfile>) {
                    val userProfile = response.data
                    if (response.code == HiResponse.SUCCESS && userProfile != null) {
                        updateUI(userProfile)
                    } else {
                        showToast(response.msg)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    showToast(throwable.message)
                }
            })
    }

    private fun queryCourseNotice() {
        ApiFactory.create(AccountApi::class.java).notice()
            .enqueue(object : HiCallback<CourseNotice> {
                override fun onSuccess(response: HiResponse<CourseNotice>) {
                    if (response.data != null && response.data!!.total > 0) {
                        notify_count.setText(response.data!!.total.toString())
                        notify_count.visibility = View.VISIBLE
                    }
                }

                override fun onFailed(throwable: Throwable) {
                }
            })
    }


    private fun showToast(message: String?) {
        if (message == null) return
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(userProfile: UserProfile) {
        user_name.text =
            if (userProfile.isLogin) userProfile.userName else getString(R.string.profile_not_login)
        login_desc.text = if (userProfile.isLogin) "欢迎回来" else "解锁全部功能"

        if (userProfile.isLogin) {
            user_avatar.loadCircle(userProfile.avatar)
        } else {
            user_avatar.setImageResource(R.drawable.ic_launcher_background)
            user_name.setOnClickListener {
                ARouter.getInstance().build("/account/login")
                    .navigation(activity, REQUEST_CODE_LOGIN_PROFILE)
            }
        }

        tab_item_collection.text = spannableTabItem(
            userProfile.facoriteCount,
            getString(R.string.profile_tab_item_collection)
        )
        tab_item_history.text = spannableTabItem(
            userProfile.facoriteCount,
            getString(R.string.profile_tab_item_history)
        )
        tab_item_learn.text = spannableTabItem(
            userProfile.facoriteCount,
            getString(R.string.profile_tab_item_learn)
        )

        updateBanner(userProfile.bannerNoticeList)


    }

    private fun updateBanner(noticeList: List<Notice>?) {
        if (noticeList == null || noticeList.size <= 0) return
        var models = mutableListOf<HiBannerMo>()
        noticeList.forEach {
            var hiBannerMo = object : HiBannerMo() {}
            hiBannerMo.url = it.cover
            models.add(hiBannerMo)
        }
        hi_banner.setBannerData(R.layout.layout_profile_banner_item, models)
        hi_banner.setBindAdapter { viewHolder, mo, position ->
            if (viewHolder == null) return@setBindAdapter
            val imageView = viewHolder.findViewById<ImageView>(R.id.banner_item_imageview)
            imageView.loadCorner(mo.url, HiDisplayUtil.dp2px(context, 10f))
        }
        hi_banner.setOnBannerClickListener { viewHolder, bannerMo, position ->
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(noticeList[position].url))
            startActivity(intent)
        }
        hi_banner.visibility = View.VISIBLE
    }

    private fun spannableTabItem(topText: Int, bottomText: String): CharSequence? {
        val spanStr = topText.toString()
        var ssb = SpannableStringBuilder()
        var ssTop = SpannableString(spanStr)
        ssTop.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color_000)),
            0,
            ssTop.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssTop.setSpan(
            AbsoluteSizeSpan(18, true), 0, ssTop.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssTop.setSpan(
            StyleSpan(Typeface.BOLD), 0, ssTop.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssb.append(ssTop)
        ssb.append(bottomText)
        return ssb
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOGIN_PROFILE && resultCode == Activity.RESULT_OK && data != null){
            queryLoginUserData()
        }
    }
}