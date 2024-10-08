package com.lfc.wemeet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.lfc.framework.base.BaseActivity;
import com.lfc.framework.bmob.BmobManager;
import com.lfc.framework.entity.Constants;
import com.lfc.framework.utils.SpUtils;
import com.lfc.wemeet.MainActivity;
import com.lfc.wemeet.R;

/**
 * @Author: LaiFuCheng
 * @CreateTime: 2024/7/31
 * @Profile: 启动页
 */
public class IndexActivity extends BaseActivity {

    /**
     * 1.把启动页全屏
     * 2.延迟进入主页
     * 3.根据具体逻辑是进入主页还是引导页还是登录页
     * 4.适配刘海屏
     *
     * @param savedInstanceState
     */

    private static final int SKIP_MAIN = 1000;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            switch (message.what) {
                case SKIP_MAIN:
                    startMain();
                    break;
            }

            return false;
        }
    });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mHandler.sendEmptyMessageDelayed(SKIP_MAIN, 2 * 1000);
    }

    /**
     * 进入主页
     */
    private void startMain() {
        //1.判断app是否是第一次启动 install - first run
        SpUtils.getInstance().initSp(this);
        boolean isFirstApp = SpUtils.getInstance().getBoolean(Constants.SP_IS_FIRST_APP, true);
        Intent intent = new Intent();
        if (isFirstApp) {
            //跳转到引导页
            intent.setClass(this, GuideActivity.class);
            //非第一次启动
            SpUtils.getInstance().putBoolean(Constants.SP_IS_FIRST_APP, false);
        } else {
            //2.如果非第一次启动，判断是否曾经登录过
            String token = SpUtils.getInstance().getString(Constants.SP_TOKEN, "");
            if (TextUtils.isEmpty(token)) {
                //3.判断Bmob是否登录
                if(BmobManager.getInstance().islogin()){
                    //跳转到主页
                    intent.setClass(this, MainActivity.class);
                }else {
                    //跳转到登录页
                    intent.setClass(this, LoginActivity.class);
                }
            } else {
                //跳转到主页
                intent.setClass(this, MainActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }
}
