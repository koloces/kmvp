package com.koloces.utilslib.mvpBase;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by koloces on 2020/3/12
 */
public abstract class BasePresenterImpl<T extends BaseView> extends BasePresenter {
    protected String TAG;
    protected T mView;
    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;

    public BasePresenterImpl(T view) {
        TAG = this.getClass().getSimpleName();
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
        unDisposable();
    }

    @Override
    public void start() {

    }

    /**
     * 将Disposable添加
     * 网络请求后将{@link com.koloces.utilslib.api.http.OnHttpResultListener}中{ getDisposable(Disposable disposable) }方法回调值加入
     * @param subscription
     */
    @Override
    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);

    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    @Override
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

}
