package com.lfc.framework.base;

import android.os.Bundle;

import com.lfc.framework.utils.SystemUI;

/**
 * @Author: LaiFuCheng
 * @CreateTime: 2024/7/30
 * @Profile: 沉浸式
 */

public class BaseUIActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemUI.fixSystemUI(this);
    }
}
