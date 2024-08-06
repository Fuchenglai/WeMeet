package com.lfc.wemeet.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lfc.framework.base.BaseUIActivity;
import com.lfc.framework.bmob.BmobManager;
import com.lfc.framework.bmob.User;
import com.lfc.framework.entity.Constants;
import com.lfc.framework.manager.DialogManager;
import com.lfc.framework.utils.JumpUtils;
import com.lfc.framework.utils.LogUtils;
import com.lfc.framework.utils.SpUtils;
import com.lfc.framework.utils.ToastUtils;
import com.lfc.framework.view.DialogView;
import com.lfc.framework.view.LoadingView;
import com.lfc.framework.view.TouchPictureV;
import com.lfc.wemeet.MainActivity;
import com.lfc.wemeet.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * @Author: LaiFuCheng
 * @CreateTime: 2024/7/31
 * @Profile: 登录页
 */
public class LoginActivity extends BaseUIActivity implements View.OnClickListener {

    /**
     * 电话号码
     */
    private EditText mEtPhone;
    /**
     * 验证码
     */
    private EditText mEtCode;
    /**
     * 发送
     */
    private Button mBtnSendCode;
    /**
     * 登录
     */
    private Button mBtnLogin;
    /**
     * 测试登录
     */
    private TextView mTvTestLogin;
    /**
     * <u>用户协议</u>
     */
    private TextView mTvUserAgreement;
    private final int SEND_LOGIN_MESSAGE = 1001;

    //60s倒计时
    private static int time = 60;

    private DialogView mDialog;
    private LoadingView mLodingView;
    private TouchPictureV mtouchPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initDialog();
    }

    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SEND_LOGIN_MESSAGE:
                    time--;
                    mBtnSendCode.setText(time + " s");

                    if (time > 0) {
                        //如果倒计时没有结束，则继续循环
                        myHandler.sendEmptyMessageDelayed(SEND_LOGIN_MESSAGE, 1000);
                    } else {

                        mBtnSendCode.setBackground(getResources().getDrawable(R.drawable.login_btn_bg));
                        mBtnSendCode.setEnabled(true);
                        mBtnSendCode.setText(getString(R.string.text_login_send_again));
                    }
                    break;
            }
            return false;
        }
    });

    private void initDialog() {
        mLodingView = new LoadingView(this);
        mLodingView.setCancelable(false);

        mDialog = DialogManager.getInstance().initView(this, com.lfc.framework.R.layout.dialog_login);
        mDialog.setCancelable(false);
        mtouchPic = mDialog.findViewById(com.lfc.framework.R.id.touchPic);
        mtouchPic.setViewResultListener(new TouchPictureV.OnViewResultListener() {
            @Override
            public void onResult() {
                mDialog.hide();
                sendSMS();
            }
        });
    }

    private void initView() {
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtCode = (EditText) findViewById(R.id.et_code);
        mBtnSendCode = (Button) findViewById(R.id.btn_send_code);
        mBtnSendCode.setOnClickListener(this);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mTvTestLogin = (TextView) findViewById(R.id.tv_test_login);
        mTvTestLogin.setOnClickListener(this);
        mTvUserAgreement = (TextView) findViewById(R.id.tv_user_agreement);
        mTvUserAgreement.setOnClickListener(this);

        String phone = SpUtils.getInstance().getString(Constants.SP_PHONE, "");
        if (!TextUtils.isEmpty(phone)) {
            mEtPhone.setText(phone);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.btn_send_code:
                mDialog.show();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_test_login:
                //testLogin();
                break;
            case R.id.tv_user_agreement:
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show(this, R.string.toast_login_phone_null);
            return;
        }
        String code = mEtCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.show(this, R.string.toast_login_code_null);
            return;
        }
        mLodingView.show(getString(R.string.text_login_now_login_text));
        BmobManager.getInstance().signOrLoginByMobilePhone(phone, code, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    //登录成功
                    mLodingView.hide();
                    SpUtils.getInstance().initSp(LoginActivity.this);
                    SpUtils.getInstance().putString(Constants.SP_PHONE, phone);
                    ToastUtils.show(LoginActivity.this, R.string.toast_success);
                    JumpUtils.goNext(LoginActivity.this, MainActivity.class, true);
                } else {
                    //登录失败
                    mLodingView.hide();
                    ToastUtils.show(LoginActivity.this, R.string.toast_error);
                }
            }
        });
    }

    /**
     * 发送验证码
     */
    private void sendSMS() {
        //1.获取手机号
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show(this, R.string.toast_login_phone_null);
            return;
        }
        //2.请求短信验证码
        BmobManager.getInstance().requestSMS(phone, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    mBtnSendCode.setEnabled(false);
                    mBtnSendCode.setBackground(getResources().getDrawable(R.drawable.login_btn_bg_no));
                    myHandler.sendEmptyMessage(SEND_LOGIN_MESSAGE);
                    ToastUtils.show(LoginActivity.this, R.string.toast_success);
                } else {
                    ToastUtils.show(LoginActivity.this, R.string.toast_error);
                    LogUtils.i(e.toString());
                }
            }
        });
    }
}
