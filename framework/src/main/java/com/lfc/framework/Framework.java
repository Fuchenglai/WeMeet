package com.lfc.framework;


import android.content.Context;

import com.lfc.framework.bmob.BmobManager;
import com.lfc.framework.utils.SpUtils;

/**
 * Framework的路口，一个单例类
 */
public class Framework {
    private volatile static Framework myFramework;

    private Framework(){

    }

    public static Framework getFramework(){
        if(myFramework==null){
            synchronized (Framework.class){
                if(myFramework==null){
                    myFramework=new Framework();
                }
            }
        }
        return myFramework;
    }

    /**
     * 初始化框架
     * @param context
     */
    public void initFramework(Context context){
        SpUtils.getInstance().initSp(context);
        BmobManager.getInstance().initBomb(context);
        //CloudManager.getInstance().initCloud(context);
    }

}
