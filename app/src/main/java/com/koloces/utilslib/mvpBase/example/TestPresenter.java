package com.koloces.utilslib.mvpBase.example;

import com.koloces.utilslib.api.example.TestBean;
import com.koloces.utilslib.api.example.TestNetWorkLogin;
import com.koloces.utilslib.api.http.OnHttpResultListener;
import com.koloces.utilslib.mvpBase.BasePresenterImpl;
import com.koloces.utilslib.utils.ToastUtils;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

/**
 * Created by koloces on 2020/3/12
 */
public class TestPresenter extends BasePresenterImpl<TestView> {
    private TestNetWorkLogin netLogin;
    public TestPresenter(TestView view) {
        super(view);
//        netLogin = new TestNetWorkLogin();
    }

    /**
     * 模拟从View中获取数据
     */
    public void toLogin(){
        String phone = mView.getPhone();
        String psw = mView.getPsw();
        boolean agreement = mView.isAgreement();
        toLogin(phone,psw,agreement);
    }

    /**
     * 模拟从activity的控件中直接获取数据
     * @param phone
     * @param psw
     * @param isAgreement
     */
    public void toLogin(String phone,String psw,boolean isAgreement){
        if (!isAgreement){
            ToastUtils.toast("请先同意协议");
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("captcha", psw);
        netLogin.login(map, new OnHttpResultListener<TestBean>() {
            @Override
            public void onSuccess(TestBean result) {
                mView.loginSuccess();
            }

            @Override
            public void onFailed(int errorCode,String msg) {
                mView.showError(msg);
            }

            @Override
            public void getDisposable(Disposable disposable) {
                /**
                 * 网络请求完毕要加入这句话,以免内存泄漏
                 */
                addDisposable(disposable);
            }

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mView.hideLoading();
            }
        });
    }
}
