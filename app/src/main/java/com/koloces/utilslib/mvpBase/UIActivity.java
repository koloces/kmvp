package com.koloces.utilslib.mvpBase;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.koloces.utilslib.dialog.LoadingDialog;
import com.koloces.utilslib.utils.PermissionsUtils;
import com.koloces.utilslib.utils.ToastUtils;
import com.koloces.utilslib.utils.activity.ActivityUtils;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

/**
 * Created by koloces on 2020/3/10
 * Activity基类
 */
public abstract class UIActivity<T extends BasePresenter> extends QMUIActivity implements BaseView {
    //打log时使用
    protected String TAG;
    protected ViewGroup mView;
    //可以在项目中自定义等待dialog重写实现
    public Dialog mLoadingDialog;
    protected T mPresenter;
    protected UIActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mActivity = this;
        mPresenter = initPresenter();

        ActivityUtils.getInstance().addActivity(this);
        setBeforeSetContentView();
        mView = (ViewGroup) LayoutInflater.from(this).inflate(getLayoutId(), null);
        setContentView(mView);
        initAfterBindLayout();
        initView();
        if (mPresenter != null) {
            getLifecycle().addObserver(mPresenter);
        }
    }

    /**
     * 设置布局
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 黄油刀绑定
     */
    public void initAfterBindLayout(){}

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化presenter
     * @return
     */
    protected abstract T initPresenter();

    /**
     * 设置状态栏白色字体图标
     * @return
     */
    protected boolean setStatusTxtWhite(){
        return QMUIStatusBarHelper.setStatusBarDarkMode(this);
    }

    /**
     * 设置状态栏黑色字体图标
     * @return
     */
    protected boolean setStatusTxtBlack(){
        return QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

    /**
     * 是否允许左滑关闭页面 默认不可以
     * @return
     */
    @Override
    protected boolean canDragBack() {
        return false;
    }

    /**
     * 在设置布局界面之前的方法(如果有需要重写该方法即可)
     */
    private void setBeforeSetContentView() {

    }

    /**
     * 页面文字大小是否跟随系统变化,默认不变化
     * @return
     */
    public boolean isTextSizeChange(){
        return false;
    }

    @Override
    public Resources getResources() {
        if (isTextSizeChange()){
            return super.getResources();
        }
        // 字体大小不跟随系统
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 动态申请权限
     * @param permissionsResult     权限回调
     * @param permissions           所需要的权限数组
     */
    protected void checkPermissions(PermissionsUtils.IPermissionsResult permissionsResult, String... permissions){
        if (permissions == null || permissions.length == 0){
            return;
        }
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        if (mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(this);
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

    /**
     * 禁用换肤{@link com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton} 设置背景灯style可以生效
     * @return
     */
    @Override
    protected boolean useQMUISkinLayoutInflaterFactory() {
        return false;
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    protected void onDestroy() {
        ActivityUtils.getInstance().removeActivity(this);
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
