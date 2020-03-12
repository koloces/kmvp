package com.koloces.utilslib.mvpBase;

import android.content.Context;

/**
 * Created by koloces on 2020/3/12
 * MVP BaseView
 */
public interface BaseView {
    /**
     * 显示loading
     */
    void showLoading();

    /**
     * 显示loading带文字
     * @param str
     */
    void showLoading(String str);

    /**
     * loading结束
     */
    void hideLoading();

    /**
     * 显示错误信息
     *
     * @param msg
     */
    void showError(String msg);

    /**
     * 获取上下文
     * @return
     */
    Context getContext();
}
