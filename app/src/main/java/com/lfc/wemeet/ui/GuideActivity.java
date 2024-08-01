package com.lfc.wemeet.ui;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.lfc.framework.base.BasePageAdapter;
import com.lfc.framework.base.BaseUIActivity;
import com.lfc.wemeet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */

public class GuideActivity extends BaseUIActivity {


    private ImageView ivMusicSwitch;
    private TextView tvGuideSkip;
    private ImageView ivPoint1;
    private ImageView ivPoint2;
    private ImageView ivPoint3;
    private ViewPager mViewPager;

    /**
     * 1.ViewPager : 适配器 | 帧动画播放
     * 2.小圆点的逻辑
     * 3.歌曲的播放
     * 4.属性动画旋转
     * 5.跳转
     */

    private View view1;
    private View view2;
    private View view3;

    private List<View> mPageList = new ArrayList<>();
    private BasePageAdapter mPageAdapter;

    private ImageView iv_guide_star;
    private ImageView iv_guide_night;
    private ImageView iv_guide_smile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        ivMusicSwitch = (ImageView) findViewById(R.id.iv_music_switch);
        tvGuideSkip = (TextView) findViewById(R.id.tv_guide_skip);
        ivPoint1 = (ImageView) findViewById(R.id.iv_point_1);
        ivPoint2 = (ImageView) findViewById(R.id.iv_point_2);
        ivPoint3 = (ImageView) findViewById(R.id.iv_point_3);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        view1 = View.inflate(this, R.layout.layout_page_guide1, null);
        view2 = View.inflate(this, R.layout.layout_page_guide2, null);
        view3 = View.inflate(this, R.layout.layout_page_guide3, null);

        mPageList.add(view1);
        mPageList.add(view2);
        mPageList.add(view3);

        //预加载
        mViewPager.setOffscreenPageLimit(mPageList.size());

        mPageAdapter = new BasePageAdapter(mPageList);
        mViewPager.setAdapter(mPageAdapter);

        //帧动画
        iv_guide_star = view1.findViewById(R.id.iv_start);
        iv_guide_night = view2.findViewById(R.id.iv_night);
        iv_guide_smile = view3.findViewById(R.id.iv_smile);

        //播放帧动画
        AnimationDrawable animatStart = (AnimationDrawable) iv_guide_star.getBackground();
        animatStart.start();
        AnimationDrawable animatNight = (AnimationDrawable) iv_guide_night.getBackground();
        animatNight.start();
        AnimationDrawable animatSmile = (AnimationDrawable) iv_guide_smile.getBackground();
        animatSmile.start();
    }
}
