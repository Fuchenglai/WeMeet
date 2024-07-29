package com.lfc.framework;


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
}
