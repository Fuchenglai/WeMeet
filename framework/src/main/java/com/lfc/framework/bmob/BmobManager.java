package com.lfc.framework.bmob;

import android.content.Context;

import com.lfc.framework.utils.LogUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * @Author: LaiFuCheng
 * @CreateTime: 2024/8/5
 * @Profile: Bmob管理类
 */
public class BmobManager extends BmobObject {

    private static final String BMOB_APP_ID = "66d010aeac119a0d64e0672dba9d1807";

    //单例封装
    private volatile static BmobManager mInstance = null;

    private BmobManager() {

    }

    public static BmobManager getInstance() {
        if (mInstance == null) {
            synchronized (BmobManager.class) {
                if (mInstance == null) {
                    mInstance = new BmobManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化Bmob
     *
     * @param context
     */
    public void initBomb(Context context) {
        //域名
        //Bmob.resetDomain("http://open.cctvcloud.cn/8/");
        Bmob.initialize(context, BMOB_APP_ID);
    }

    /**
     * 发送短信
     *
     * @param phone    手机号
     * @param listener 回调
     */
    public void requestSMS(String phone, QueryListener<Integer> listener) {
        LogUtils.i("电话号码为：" + phone);
        BmobSMS.requestSMSCode(phone, "", listener);
    }

    /**
     * 通过手机号和验证码注册登录
     *
     * @param phone    手机号
     * @param smsCode  验证码
     * @param listener 回调
     */
    public void signOrLoginByMobilePhone(String phone, String smsCode, LogInListener<User> listener) {
        BmobUser.signOrLoginByMobilePhone(phone, smsCode, listener);
    }

    /**
     * 获取本地用户对象
     *
     * @return
     */
    public User getUser() {
        return BmobUser.getCurrentUser(User.class);
    }

    /**
     * 是否登陆
     *
     * @return
     */
    public boolean islogin() {
        return BmobUser.isLogin();
    }

    /**
     * 第一次上传头像
     *
     * @param nickName
     * @param file
     */
    /*public void uploadFirstPhoto(final String nickName, final File file, final OnUploadPhotoListener onUploadPhotoListener) {
        *//**
         * 1.上传文件拿到地址
         * 2.更新用户信息
         *//*
        *//*final User user = getUser();
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    //头像上传成功
                    user.setNickName(nickName);
                    user.setPhoto(bmobFile.getFileUrl());

                    user.setTokenNickName(nickName);
                    user.setTokenPhoto(bmobFile.getFileUrl());

                    //更新用户信息
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                onUploadPhotoListener.OnUploadDone();
                            }else {
                                onUploadPhotoListener.OnUploadFail(e);
                            }
                        }
                    });
                }
            }
        });*//*
    }

    /*public interface OnUploadPhotoListener {
        void OnUploadDone();

        void OnUploadFail(BmobException e);
    }*/

    /**
     * 根据电话号码查询用户
     *
     * @param phone
     */
    /*public void queryPhoneUser(String phone, FindListener<User> listener) {
        baseQuery("mobilePhoneNumber", phone, listener);
    }*/
    /**
     * 查询所有的用户
     *
     * @param listener
     */
    /*public void queryAllUser(FindListener<User> listener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.findObjects(listener);
    }*/

    /**
     * 查询基类
     *
     * @param key
     * @param values
     * @param listener
     */
    /*public void baseQuery(String key, String values, FindListener<User> listener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo(key, values);
        query.findObjects(listener);
    }*/
}
