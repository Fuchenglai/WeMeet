package com.lfc.framework.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.lfc.framework.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log不光作为日志的打印，还可以记录日志 -> File
 */
public class LogUtils {

    private static SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    /**
     * 打印info
     *
     * @param text
     */
    public static void i(String text) {
        if (BuildConfig.LOG_DEBUG) {
            if (!TextUtils.isEmpty(text)) {
                Log.i(BuildConfig.LOG_TAG, text);
                //writeToFile(text);
            }
        }
    }

    /**
     * 打印error
     *
     * @param text
     */
    public static void e(String text) {
        if (BuildConfig.LOG_DEBUG) {
            if (!TextUtils.isEmpty(text)) {
                Log.e(BuildConfig.LOG_TAG, text);
                //writeToFile(text);
            }
        }
    }

    /**
     * android 10之前的写法
     */
    /*private static void writeToFile(String text) {
        //文件路径
        String fileName = "/sdcard/Meet/Meet.log";
        //时间 + 内存
        String log = mySimpleDateFormat.format(new Date()) + " " + text + "\n";
        //检查父路径
        File fileGroup = new File("/sdcard/Meet/");
        if (!fileGroup.exists()) {
            fileGroup.mkdir();
        }
        //开始写入
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = new FileOutputStream(fileName, true);
            //编码问题 GBK 正确的存入中文
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(fileOutputStream, Charset.forName("gbk"))
            );
            bufferedWriter.write(log);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }*/

}
