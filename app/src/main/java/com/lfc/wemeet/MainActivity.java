package com.lfc.wemeet;

import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.lfc.framework.base.BaseUIActivity;
import com.lfc.framework.manager.MediaPlayerManager;
import com.lfc.framework.utils.LogUtils;


public class MainActivity extends BaseUIActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayerManager mediaPlayerManager = new MediaPlayerManager();
        AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.guide);
        mediaPlayerManager.startPlay(fileDescriptor);

        mediaPlayerManager.setOnProgressListener(new MediaPlayerManager.OnMusicProgressListener() {
            @Override
            public void OnProgress(int progress, int pos) {
                LogUtils.e("pos" + pos);
            }
        });
    }

}
