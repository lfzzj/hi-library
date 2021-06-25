package com.lf.hi.library.logic;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.lf.common.tab.HiFragmentTabView;
import com.lf.common.tab.HiTabViewAdapter;
import com.lf.hi.library.R;
import com.lf.hi.library.fragment.CategoryFragment;
import com.lf.hi.library.fragment.FavoriteFragment;
import com.lf.hi.library.fragment.HomePageFragment;
import com.lf.hi.library.fragment.ProfileFragment;
import com.lf.hi.library.fragment.RecommendFragment;
import com.lf.hi.ui.tab.bottom.HiTabBottomInfo;
import com.lf.hi.ui.tab.bottom.HiTabBottomLayout;
import com.lf.hi.ui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.library.logic$
 * @ClassName: MainActivityLogic$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 14:39$
 * @Description:将MainActivity的一些逻辑内聚在这，让MainFragment更加清爽
 */
public class MainActivityLogic {
    private HiFragmentTabView fragmentTabView;
    private HiTabBottomLayout hiTabBottomLayout;
    private List<HiTabBottomInfo<?>> infoList;
    private ActivityProvider activityProvider;
    private int currentItemIndex;
    private final static String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";

    public MainActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        this.activityProvider = activityProvider;
        //fix 不保留活动导致的fragment重叠问题
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID);
        }
        initTabBottom();
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex);

    }

    public HiFragmentTabView getFragmentTabView() {
        return fragmentTabView;
    }

    public HiTabBottomLayout getHiTabBottomLayout() {
        return hiTabBottomLayout;
    }

    public List<HiTabBottomInfo<?>> getInfoList() {
        return infoList;
    }

    private void initTabBottom() {
        hiTabBottomLayout = activityProvider.findViewById(R.id.tab_bottom_layout);
        hiTabBottomLayout.setTabAlpha(0.85f);
        infoList = new ArrayList<>();
        int defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor);

        HiTabBottomInfo infoHome = new HiTabBottomInfo(
                "首页",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_home),
                null,
                defaultColor,
                tintColor
        );
        infoHome.fragment = HomePageFragment.class;

        HiTabBottomInfo infoFavorite = new HiTabBottomInfo(
                "收藏",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_favorite),
                null,
                defaultColor,
                tintColor
        );
        infoFavorite.fragment = FavoriteFragment.class;

        HiTabBottomInfo infoCategory = new HiTabBottomInfo(
                "分类",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_category),
                null,
                defaultColor,
                tintColor
        );
        infoCategory.fragment = CategoryFragment.class;

//        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_jetpack, null)
//        val infoCategory = HiTabBottomInfo < String > (
//                "分类",
//                bitmap,
//                bitmap
//        )

        HiTabBottomInfo infoRecommend = new HiTabBottomInfo(
                "推荐",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_commend),
                null,
                defaultColor,
                tintColor
        );
        infoRecommend.fragment = RecommendFragment.class;

        HiTabBottomInfo infoProfile = new HiTabBottomInfo(
                "我的",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_mine),
                null,
                defaultColor,
                tintColor
        );
        infoProfile.fragment = ProfileFragment.class;

        infoList.add(infoHome);
        infoList.add(infoFavorite);
        infoList.add(infoCategory);
        infoList.add(infoRecommend);
        infoList.add(infoProfile);
        hiTabBottomLayout.inflateInfo(infoList);
        initFragmentTabView();
        hiTabBottomLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabBottomInfo<?>>() {
            @Override
            public void OnTabSelectedChange(int index, @Nullable HiTabBottomInfo<?> prevInfo, @Nullable HiTabBottomInfo<?> nextInfo) {
                fragmentTabView.setCurrentItem(index);
                MainActivityLogic.this.currentItemIndex = index;
            }
        });
        hiTabBottomLayout.defaultSelected(infoList.get(currentItemIndex));
    }

    private void initFragmentTabView() {
        HiTabViewAdapter tabViewAdapter = new HiTabViewAdapter(activityProvider.getSupportFragmentManager(), infoList);
        fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view);
        fragmentTabView.setAdapter(tabViewAdapter);
    }

    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }

}
