package com.lfc.wemeet;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.lfc.framework.base.BaseUIActivity;
import com.lfc.framework.bmob.BmobManager;
import com.lfc.framework.bmob.User;
import com.lfc.framework.utils.ToastUtils;


public class MainActivity extends BaseUIActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*MediaPlayerManager mediaPlayerManager = new MediaPlayerManager();
        AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.guide);
        mediaPlayerManager.startPlay(fileDescriptor);

        mediaPlayerManager.setOnProgressListener(new MediaPlayerManager.OnMusicProgressListener() {
            @Override
            public void OnProgress(int progress, int pos) {
                LogUtils.e("pos" + pos);
            }
        });*/

        User user = BmobManager.getInstance().getUser();
        ToastUtils.show(this, "user:" + user.getMobilePhoneNumber());
    }

}
