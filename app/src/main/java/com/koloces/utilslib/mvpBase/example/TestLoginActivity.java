package com.koloces.utilslib.mvpBase.example;

import android.view.View;

import com.koloces.utilslib.R;
import com.koloces.utilslib.mvpBase.UIActivity;

/**
 * Created by koloces on 2020/3/12
 * 例子:登录界面
 */
public class TestLoginActivity extends UIActivity<TestPresenter> implements TestView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //点击事件
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toLogin();
//                mPresenter.toLogin("12345678910","112233",false);
            }
        };
    }

    @Override
    protected TestPresenter initPresenter() {
        return new TestPresenter(this);
    }

    @Override
    public String getPsw() {
        return "112233";
    }

    @Override
    public String getPhone() {
        return "12345678910";
    }

    @Override
    public boolean isAgreement() {
        return false;
    }

    @Override
    public void loginSuccess() {
//        ActivityUtils.getInstance().toNextActivity();
    }
}
