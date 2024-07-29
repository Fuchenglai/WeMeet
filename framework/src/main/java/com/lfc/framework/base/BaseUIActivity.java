package com.lfc.framework.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lfc.framework.utils.SystemUI;

public class BaseUIActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemUI.fixSystemUI(this);
    }
}
