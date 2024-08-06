package com.lfc.wemeet.ui;


import android.animation.ObjectAnimator;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.lfc.framework.base.BasePageAdapter;
import com.lfc.framework.base.BaseUIActivity;
import com.lfc.framework.manager.MediaPlayerManager;
import com.lfc.framework.utils.AnimUtils;
import com.lfc.framework.utils.JumpUtils;
import com.lfc.wemeet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */

public class GuideActivity extends BaseUIActivity implements View.OnClickListener {


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

    private MediaPlayerManager mGuideMusic;

    private ObjectAnimator mAnim;


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

        ivMusicSwitch.setOnClickListener(this);
        tvGuideSkip.setOnClickListener(this);

        /**
         * View.inflate() 将 XML 布局文件转换成对应的 View 对象。
         * 它解析 XML 文件，创建布局中的所有视图，并返回根视图
         */
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
        /**
         * 选择使用findViewById()还是View.findViewById()主要取决于你当前的上下文。
         * 如果你是在Activity中，并且需要查找根布局中的视图，使用Activity的findViewById()。
         * 如果你是在某个View中，并且需要查找它的子视图，使用View的findViewById()
         */

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

        //小圆点逻辑
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //歌曲的逻辑
        startMusic();
    }

    /**
     * 播放音乐
     */
    private void startMusic() {
        mGuideMusic = new MediaPlayerManager();
        mGuideMusic.setLooping(true);
        AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.guide);
        mGuideMusic.startPlay(file);

        //旋转动画
        mAnim = AnimUtils.rotation(ivMusicSwitch, 3 * 1000);
        mAnim.start();
    }

    /**
     * 动态选择小圆点
     *
     * @param position
     */
    private void selectPoint(int position) {
        switch (position) {
            case 0:
                ivPoint1.setImageResource(R.mipmap.img_guide_point_p);
                ivPoint2.setImageResource(R.mipmap.img_guide_point);
                ivPoint3.setImageResource(R.mipmap.img_guide_point);
                break;
            case 1:
                ivPoint1.setImageResource(R.mipmap.img_guide_point);
                ivPoint2.setImageResource(R.mipmap.img_guide_point_p);
                ivPoint3.setImageResource(R.mipmap.img_guide_point);
                break;
            case 2:
                ivPoint1.setImageResource(R.mipmap.img_guide_point);
                ivPoint2.setImageResource(R.mipmap.img_guide_point);
                ivPoint3.setImageResource(R.mipmap.img_guide_point_p);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_music_switch:
                if (mGuideMusic.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PAUSE) {
                    mAnim.start();
                    mGuideMusic.continuePlay();
                    ivMusicSwitch.setImageResource(R.mipmap.img_guide_music);
                } else if (mGuideMusic.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PLAY) {
                    mAnim.pause();
                    mGuideMusic.pausePlay();
                    ivMusicSwitch.setImageResource(R.mipmap.img_guide_music_off);
                }
                break;
            case R.id.tv_guide_skip:
                JumpUtils.goNext(this, LoginActivity.class, true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGuideMusic.stopPlay();
    }
}
