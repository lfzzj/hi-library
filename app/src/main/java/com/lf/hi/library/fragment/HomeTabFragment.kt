package com.lf.hi.library.fragment

import android.os.Bundle
import com.lf.common.ui.component.HiAbsListFragment

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.fragment$
 * @ClassName:      HomeTabFragment$
 * @Author:         LF
 * @CreateDate:     2021/8/9$ 16:00$
 * @Description:
 */
class HomeTabFragment : HiAbsListFragment() {

    companion object{
        fun newInstance(categoryId: String): HomeTabFragment {
            val args = Bundle()
            args.putString("categoryId", categoryId)
            val fragment = HomeTabFragment()
            fragment.arguments = args
            return fragment
        }
    }


}