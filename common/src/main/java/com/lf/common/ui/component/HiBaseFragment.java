package com.lf.common.ui.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.common.ui.component$
 * @ClassName: HiBaseFragment$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 11:21$
 * @Description:
 */
public abstract class HiBaseFragment extends Fragment {
    protected View layoutView;

    @LayoutRes
    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(getLayoutId(),container,false);
        return layoutView;
    }
}
