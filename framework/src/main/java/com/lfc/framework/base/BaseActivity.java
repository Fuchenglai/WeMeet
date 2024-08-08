package com.lfc.framework.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LaiFuCheng
 * @CreateTime: 2024/8/1
 * @Profile: 基类，所有的Activity都要继承这个Activity.在这里实现所有的统一功能：语言切换，请求权限
 * API级别23以上的应用，一定要要在运行时检查和请求权限
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 声明所需权限
     */
    private String[] mStrPermission = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    /**
     * 保存没有同意的权限
     */
    private List<String> mPerList = new ArrayList<>();

    /**
     * 保存没有同意的失败的权限
     */
    private List<String> mPerNoList = new ArrayList<>();


    private OnPermissionsResult permissionsResult;

    private int requestCode;

    /**
     * 一个方法请求权限
     *
     * @param requestCode
     * @param permissionsResult
     */
    protected void request(int requestCode, OnPermissionsResult permissionsResult) {
        if (!checkPermissionAll()) {
            requestPermissionAll(requestCode, permissionsResult);
        }
    }

    /**
     * 检查是否具有某个权限
     *
     * @param permission
     * @return
     */
    protected boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = checkSelfPermission(permission);
            return check == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    /**
     * 判断是否需要申请权限
     *
     * @return
     */
    protected boolean checkPermissionAll() {
        mPerList.clear();
        for (String per : mStrPermission) {
            boolean checked = checkPermission(per);
            if (!checked) {
                mPerList.add(per);
            }
        }
        return mPerList.size() > 0 ? false : true;
    }

    /**
     * 请求权限
     *
     * @param permissions
     * @param requestCode
     */
    protected void requestPermission(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    /**
     * 申请所有权限
     *
     * @param requestCode
     */
    protected void requestPermissionAll(int requestCode, OnPermissionsResult permissionsResult) {
        this.requestCode = requestCode;
        this.permissionsResult = permissionsResult;
        requestPermission(mPerList.toArray(new String[mPerList.size()]), requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mPerNoList.clear();
        if (requestCode == requestCode) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        //有失败的权限
                        mPerNoList.add(permissions[i]);
                    }
                }
            }
            if (permissionsResult != null) {
                if (mPerNoList.size() == 0) {
                    permissionsResult.onSuccess();
                } else {
                    permissionsResult.onFail(mPerNoList);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 判断窗口权限
     *
     * @return
     */
    protected boolean checkWindowPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this);
        }
        return true;
    }

    /**
     * 请求窗口权限
     *
     * @param requestCode
     */
    protected void requestWindowPermissions(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);

    }

    protected interface OnPermissionsResult {
        void onSuccess();

        void onFail(List<String> noPermissions);
    }
}
