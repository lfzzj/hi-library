package com.lf.hi.library.demo.tab;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lf.hi.library.R;
import com.lf.hi.ui.tab.common.IHiTabLayout;
import com.lf.hi.ui.tab.top.HiTabTopInfo;
import com.lf.hi.ui.tab.top.HiTabTopLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.library.demo.tab$
 * @ClassName: HiTabTopDemoActivity$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 16:29$
 * @Description:
 */
public class HiTabTopDemoActivity extends AppCompatActivity {

    String[] tabsStr = new String[]{
            "热门",
            "服装",
            "数码",
            "鞋子",
            "零食",
            "家电",
            "汽车",
            "百货",
            "家居",
            "装修",
            "运动"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_tab_top_demo);
        initTabTop();
    }

    private void initTabTop() {
        HiTabTopLayout hiTabTopLayout = findViewById(R.id.tab_top_layout);
        List<HiTabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = getResources().getColor(R.color.tabBottomTintColor);
        for (String s : tabsStr) {
            HiTabTopInfo<?> info = new HiTabTopInfo<>(s, defaultColor, tintColor);
            infoList.add(info);
        }
        hiTabTopLayout.inflateInfo(infoList);
        hiTabTopLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabTopInfo<?>>() {
            @Override
            public void OnTabSelectedChange(int index, @Nullable HiTabTopInfo<?> prevInfo, @Nullable HiTabTopInfo<?> nextInfo) {
                Toast.makeText(HiTabTopDemoActivity.this, nextInfo.name, Toast.LENGTH_SHORT).show();
            }
        });
        hiTabTopLayout.defaultSelected(infoList.get(0));
    }
}
