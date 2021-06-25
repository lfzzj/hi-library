package com.lf.hi.library;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lf.common.ui.component.HiBaseActivity;
import com.lf.hi.library.logic.MainActivityLogic;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.library$
 * @ClassName: MainActivity$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 14:36$
 * @Description:
 */
public class MainActivity extends HiBaseActivity implements MainActivityLogic.ActivityProvider {

    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityLogic = new MainActivityLogic(this,savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        activityLogic.onSaveInstanceState(outState);
    }
}
