package com.lf.hi.library.fragment;

import com.lf.common.ui.component.HiBaseFragment;
import com.lf.hi.library.R;
import com.lf.nav_annotation.Destination;
import com.lf.nav_annotation.FragmentDestination;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.library.fragment$
 * @ClassName: CategoryFragment$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 14:27$
 * @Description:
 */
@FragmentDestination(pageUrl = "main/category")
public class CategoryFragment extends HiBaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }
}
