package com.koloces.utilslib.mvpBase;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koloces.utilslib.dialog.LoadingDialog;
import com.koloces.utilslib.utils.ToastUtils;

/**
 * Created by koloces on 2020/3/12
 */
public abstract class UIFragment<T extends BasePresenter> extends Fragment implements BaseView  {

    protected String TAG;
    protected View mView;
    protected Context mContext;
    //可以在项目中自定义等待dialog重写实现
    public Dialog mLoadingDialog;
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(getActivity()).inflate(getLayout(),null);
        initAfterBindLayout();
        mPresenter = initPresenter();
        initView();
        if (mPresenter != null) {
            getLifecycle().addObserver(mPresenter);
        }
        return mView;
    }
    /**
     * 获取布局ID
     * @return
     */
    protected abstract int getLayout();

    /**
     * 绑定黄油刀用
     */
    public void initAfterBindLayout(){}

    protected abstract void initView();

    protected abstract T initPresenter();

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        if (mContext == null)return;
        if (mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(mContext);
        }
        mLoadingDialog.show();
    }

    /**
     * 显示loading带文字
     * @param str
     */
    @Override
    public void showLoading(String str) {
        if (!mLoadingDialog.isShowing()){
            showLoading();
        }
        if (mLoadingDialog instanceof LoadingDialog) {
            ((LoadingDialog) mLoadingDialog).setText(str);
        }
    }

    /**
     * loading结束
     */
    @Override
    public void hideLoading() {
        if (mLoadingDialog != null){
            try {
                mLoadingDialog.dismiss();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示错误信息
     * @param msg
     */
    @Override
    public void showError(String msg) {
        ToastUtils.toast(msg);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onDestroy() {
        if (mLoadingDialog != null){
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        if (mPresenter != null) {
            getLifecycle().removeObserver(mPresenter);
        }
        super.onDestroy();
    }
}
