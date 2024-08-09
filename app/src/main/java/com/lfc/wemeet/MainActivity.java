package com.lfc.wemeet;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lfc.framework.base.BaseUIActivity;
import com.lfc.framework.utils.ToastUtils;
import com.lfc.wemeet.fragment.ChatFragment;
import com.lfc.wemeet.fragment.MeFragment;
import com.lfc.wemeet.fragment.SquareFragment;
import com.lfc.wemeet.fragment.StarFragment;

import java.util.List;


public class MainActivity extends BaseUIActivity implements View.OnClickListener {

    private FrameLayout mMMainLayout;

    /**
     * 星球
     */
    private TextView mTvStar;
    private ImageView mIvStar;
    private LinearLayout mLlStar;
    private StarFragment mStarFragment = null;
    private FragmentTransaction mStarTransaction = null;
    /**
     * 广场
     */
    private TextView mTvSquare;
    private ImageView mIvSquare;
    private LinearLayout mLlSquare;
    private SquareFragment mSquareFragment = null;
    private FragmentTransaction mSquareTransaction = null;
    /**
     * 聊天
     */
    private TextView mTvChat;
    private ImageView mIvChat;
    private LinearLayout mLlChat;
    private ChatFragment mChatFragment = null;
    private FragmentTransaction mChatTransaction = null;

    /**
     * 我的
     */
    private TextView mTvMe;
    private ImageView mIvMe;
    private LinearLayout mLlMe;
    private MeFragment mMeFragment = null;
    private FragmentTransaction mMeTransaction = null;

    private static final int PERMISSION_CODE = 1000;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*MediaPlayerManager mediaPlayerManager = new MediaPlayerManager();
        AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.guide);
        mediaPlayerManager.startPlay(fileDescriptor);

        mediaPlayerManager.setOnProgressListener(new MediaPlayerManager.OnMusicProgressListener() {
            @Override
            public void OnProgress(int progress, int pos) {
                LogUtils.e("pos" + pos);
            }
        });*/

        /*User user = BmobManager.getInstance().getUser();
        ToastUtils.show(this, "user:" + user.getMobilePhoneNumber());*/
    }

    /**
     * 初始化View
     */
    private void initView() {
        request(PERMISSION_CODE, new OnPermissionsResult() {
            @Override
            public void onSuccess() {
                ToastUtils.show(MainActivity.this, R.string.toast_success);
            }

            @Override
            public void onFail(List<String> mNolist) {
                ToastUtils.show(MainActivity.this, mNolist.toString());
            }
        });


        mMMainLayout = (FrameLayout) findViewById(R.id.mMainLayout);
        mIvStar = (ImageView) findViewById(R.id.iv_star);
        mTvStar = (TextView) findViewById(R.id.tv_star);
        mLlStar = (LinearLayout) findViewById(R.id.ll_star);
        mLlStar.setOnClickListener(this);
        mIvSquare = (ImageView) findViewById(R.id.iv_square);
        mTvSquare = (TextView) findViewById(R.id.tv_square);
        mLlSquare = (LinearLayout) findViewById(R.id.ll_square);
        mLlSquare.setOnClickListener(this);
        mIvChat = (ImageView) findViewById(R.id.iv_chat);
        mTvChat = (TextView) findViewById(R.id.tv_chat);
        mLlChat = (LinearLayout) findViewById(R.id.ll_chat);
        mLlChat.setOnClickListener(this);
        mIvMe = (ImageView) findViewById(R.id.iv_me);
        mTvMe = (TextView) findViewById(R.id.tv_me);
        mLlMe = (LinearLayout) findViewById(R.id.ll_me);
        mLlMe.setOnClickListener(this);

        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        //星球
        if (mStarFragment == null) {
            mStarFragment = new StarFragment();
            mStarTransaction = getSupportFragmentManager().beginTransaction();
            mStarTransaction.add(R.id.mMainLayout, mStarFragment, "star");
            mStarTransaction.commit();
        }
        //广场
        if (mSquareFragment == null) {
            mSquareFragment = new SquareFragment();
            mSquareTransaction = getSupportFragmentManager().beginTransaction();
            mSquareTransaction.add(R.id.mMainLayout, mSquareFragment, "square");
            mSquareTransaction.commit();
        }
        //聊天
        if (mChatFragment == null) {
            mChatFragment = new ChatFragment();
            mChatTransaction = getSupportFragmentManager().beginTransaction();
            mChatTransaction.add(R.id.mMainLayout, mChatFragment, "chat");
            mChatTransaction.commit();
        }
        //我的
        if (mMeFragment == null) {
            mMeFragment = new MeFragment();
            mMeTransaction = getSupportFragmentManager().beginTransaction();
            mMeTransaction.add(R.id.mMainLayout, mMeFragment, "me");
            mMeTransaction.commit();
        }

        //默认选项卡
        checkMainTap(0);

        //checkToken();
    }

    /**
     * 防止重叠
     * 当应用的内存紧张的时候，系统会回收掉Fragment对象
     * 再一次进入的时候会重新创建Fragment
     * 非原来对象，我们无法控制，导致重叠
     *
     * @param fragment
     */
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (mStarFragment == null && fragment instanceof StarFragment) {
            mStarFragment = (StarFragment) fragment;
        }
        if (mSquareFragment == null && fragment instanceof SquareFragment) {
            mSquareFragment = (SquareFragment) fragment;
        }
        if (mChatFragment == null && fragment instanceof ChatFragment) {
            mChatFragment = (ChatFragment) fragment;
        }
        if (mMeFragment == null && fragment instanceof MeFragment) {
            mMeFragment = (MeFragment) fragment;
        }
    }

    /**
     * 隐藏所有fragment
     *
     * @param transaction
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        if (mStarFragment != null) {
            transaction.hide(mStarFragment);
        }
        if (mSquareFragment != null) {
            transaction.hide(mSquareFragment);
        }
        if (mChatFragment != null) {
            transaction.hide(mChatFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
    }

    /**
     * 显示fragment
     *
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            hideAllFragment(transaction);
            transaction.show(fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 切换主题选项卡
     *
     * @param index 0：星球
     *              1：广场
     *              2：聊天
     *              3：我的
     */
    private void checkMainTap(int index) {
        switch (index) {
            case 0:
                showFragment(mStarFragment);
                mIvStar.setImageResource(R.mipmap.img_star_p);
                mIvSquare.setImageResource(R.mipmap.img_square);
                mIvChat.setImageResource(R.mipmap.img_chat);
                mIvMe.setImageResource(R.mipmap.img_me);
                break;
            case 1:
                showFragment(mSquareFragment);
                mIvStar.setImageResource(R.mipmap.img_star);
                mIvSquare.setImageResource(R.mipmap.img_square_p);
                mIvChat.setImageResource(R.mipmap.img_chat);
                mIvMe.setImageResource(R.mipmap.img_me);
                break;
            case 2:
                showFragment(mChatFragment);
                mIvStar.setImageResource(R.mipmap.img_star);
                mIvSquare.setImageResource(R.mipmap.img_square);
                mIvChat.setImageResource(R.mipmap.img_chat_p);
                mIvMe.setImageResource(R.mipmap.img_me);
                break;
            case 3:
                showFragment(mMeFragment);
                mIvStar.setImageResource(R.mipmap.img_star);
                mIvSquare.setImageResource(R.mipmap.img_square);
                mIvChat.setImageResource(R.mipmap.img_chat);
                mIvMe.setImageResource(R.mipmap.img_me_p);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.ll_star:
                checkMainTap(0);
                break;
            case R.id.ll_square:
                checkMainTap(1);
                break;
            case R.id.ll_chat:
                checkMainTap(2);
                break;
            case R.id.ll_me:
                checkMainTap(3);
                break;
        }
    }
}
