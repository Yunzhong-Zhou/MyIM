package com.im.myim.activity;

import android.os.Bundle;

import com.im.myim.R;
import com.im.myim.base.BaseActivity;

/**
 * Created by Mr.Z on 2021/9/30.
 * SVIP
 */
public class SVIPActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svip);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        titleView.setTitle("SVIP");
    }
}
