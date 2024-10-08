package com.lfc.framework.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * @Author: LaiFuCheng
 * @CreateTime: 2024/8/6
 * @Profile: 自定义提示框
 */
public class DialogView extends Dialog {

    /**
     *
     * @param mContext
     * @param layout 布局
     * @param style 主题id
     * @param gravity 位置
     */
    public DialogView(Context mContext, int layout, int style, int gravity) {
        super(mContext, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
    }
}
