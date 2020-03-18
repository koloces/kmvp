package com.koloces.utilslib.mvpBase;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.koloces.utilslib.api.http.OkHttp3Utils;
import com.koloces.utilslib.utils.LogUtils;
import com.koloces.utilslib.utils.SPUtil;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

public abstract class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static BaseApplication instance;
    //app是否可见,是否在前台
    private boolean mAppResume = false;
    public static BaseApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化SP
        SPUtil.init(this);
        //初始化Log
        LogUtils.init(isDebug());
        /* 注册全局activity生命周期监听 */
        registerActivityLifecycleCallbacks(this);
        /* 注册左滑返回监听 */
        QMUISwipeBackActivityManager.init(this);
        //初始化一个全局的网络请求
        OkHttp3Utils.init();
        initApp();
    }

    /**
     * 是否是debug,true则打印Log,false不打印
     * @return
     */
    public abstract boolean isDebug();

    /**
     * 初始化各种sdk
     */
    protected abstract void initApp();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //64k
        MultiDex.install(this);
    }

    /**
     * app是否前台可见
     * @return
     */
    public boolean appIsResume(){
        return mAppResume;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        mAppResume = true;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        mAppResume = false;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
