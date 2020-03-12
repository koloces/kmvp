package com.koloces.utilslib.api.example;

import com.koloces.utilslib.api.http.OnHttpResultListener;
import com.koloces.utilslib.mvpBase.example.UtilsMainActivity;
import com.koloces.utilslib.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by koloces on 2020/3/10
 * 请求前必须先配置主Service{@link com.koloces.utilslib.api.http.RetrofitUtils}
 * 例:{@link UtilsMainActivity}
 */
public class TestBaseNetWork {
    public <T> void setSubscribe(Observable<T> observable, OnHttpResultListener<T> listener) {
        if (observable == null)return;
        if (listener != null) {
            listener.onStart();
        }
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (listener == null)return;
                        listener.getDisposable(d);
                    }

                    @Override
                    public void onNext(T t) {
                        if (listener == null)return;
                        if (t instanceof TestBaseBean){
                            if (((TestBaseBean) t).code == 1){
                                listener.onSuccess(t);
                            } else {
                                //这里处理统一的错误,比如登录失效之类的
                                listener.onFailed(((TestBaseBean) t).code,((TestBaseBean) t).msg);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener == null)return;
                        listener.onFailed(-999,e.getMessage());
                        listener.onFinish();
                    }

                    @Override
                    public void onComplete() {
                        if (listener == null)return;
                        listener.onFinish();
                    }
                });
    }
}
