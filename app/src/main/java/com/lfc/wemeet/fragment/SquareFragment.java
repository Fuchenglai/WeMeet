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
 * Founder: shaobin
 * Create Date: 2020/1/14
 * Profile: 广场
 */
public class SquareFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_square,null);
        return mview;
    }
}
