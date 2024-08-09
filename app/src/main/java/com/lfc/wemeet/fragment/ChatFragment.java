package com.lfc.wemeet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lfc.framework.base.BaseFragment;
import com.lfc.wemeet.R;


/**
 * @Author: LaiFuCheng
 * @CreateTime: 2024/8/9
 * @Profile:
 */
public class ChatFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_chat,null);
        return mview;
    }
}
