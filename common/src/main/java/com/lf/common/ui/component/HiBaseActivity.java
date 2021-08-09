package com.lf.common.ui.component;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.common.ui.component$
 * @ClassName: HiBaseActivity$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 11:20$
 * @Description:
 */
public class HiBaseActivity extends AppCompatActivity implements HiBaseActionInterface {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }
}
