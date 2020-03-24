package com.youjiang.test;

import com.koloces.utilslib.mvpBase.BasePresenter;
import com.koloces.utilslib.mvpBase.UIActivity;

public class MainActivity extends UIActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
