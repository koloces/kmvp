package com.koloces.utilslib.mvpBase;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import io.reactivex.disposables.Disposable;

/**
 * Created by koloces on 2020/3/12
 * DefaultLifecycleObserver 生命周期管理类可以回调Activity的各个生命周期
 */
public abstract class BasePresenter implements DefaultLifecycleObserver{
    //默认初始化
    public abstract void start();

    //Activity关闭把view对象置为空
    public abstract void detach();

    //将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
    public abstract void addDisposable(Disposable subscription);

    //注销所有请求
    public abstract void unDisposable();

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        detach();
    }
}
