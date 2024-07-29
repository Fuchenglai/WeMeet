package com.lfc.framework.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SystemUI {

    public static void fixSystemUI(Activity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            int vis = window.getDecorView().getSystemUiVisibility();
            window.getDecorView().setSystemUiVisibility(option | vis);
            //导航栏设置为透明状态
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
