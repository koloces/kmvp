package com.koloces.utilslib.api.http;

import io.reactivex.disposables.Disposable;

/**
 * Created by koloces on 2020/3/10
 */
public abstract class OnHttpResultListener<T> {
    /**
     * 需要显示做操作时重写该方法
     * 例如网络请求需要显示loading
     */
    public void onStart(){}

    /**
     * 请求成功
     * @param result
     */
    public abstract void onSuccess(T result);

    /**
     * 请求失败
     * @param msg 返回失败信息
     * -999是我自定义的错误,不用管直接看msg
     */
    public abstract void onFailed(int errCode,String msg);

    /**
     * 通 onStart();
     */
    public void onFinish(){}

    /**
     * 获取网络请求的Disposable以方便注销,防止内存泄漏
     * @param disposable
     */
    public abstract void getDisposable(Disposable disposable);
}
