package com.koloces.utilslib.utils;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * Created by koloces on 2020/3/12
 * 加载更多工具,可以用在recycle的刷新和加载更多
 * 在项目中使用的时候可以再做一次封装,把空白页面封装起来,
 * 网络请求也可以封装起来,根据项目需求再做封装
 */
public abstract class LoadMoreHelper<T> implements OnRefreshListener, OnLoadMoreListener {
    private int page = 0;
    private SmartRefreshLayout mSmartRefresh;
    private BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    private int singlePageNum = 10;

    /**
     * 传入刷新控件和recycler
     * @param mSmartRefresh
     * @param adapter
     */
    public LoadMoreHelper(SmartRefreshLayout mSmartRefresh, BaseQuickAdapter<T,BaseViewHolder> adapter) {
        this.mSmartRefresh = mSmartRefresh;
        this.mAdapter = adapter;
        init();
    }

    /**
     * 设置单页最多显示条数,默认10条
     * @param num
     * @return
     */
    public LoadMoreHelper setSinglePageNum(int num){
        singlePageNum = num;
        return this;
    }

    /**
     * 新数据来后加载新数据
     * @param data
     */
    public void setNewData(List<T> data){
        if (mSmartRefresh != null){
            mSmartRefresh.finishRefresh();
            mSmartRefresh.finishLoadMore();
        }
        if (data != null && data.size() > 0){
            if (mAdapter != null){
                if (page == 1){
                    mAdapter.setNewData(data);
                } else {
                    mAdapter.addData(data);
                }
            }

            /**
             * 如果数量不足单页显示条数不允许加载更多
             */
            if (mSmartRefresh != null && data.size() < singlePageNum){
                mSmartRefresh.setEnableLoadMore(false);
            } else {
                mSmartRefresh.setEnableLoadMore(true);
            }

        } else {
            if (mSmartRefresh != null){
                mSmartRefresh.setEnableLoadMore(false);
            }
            if (mAdapter != null){
                //说明没有数据,应该显示空白页
                if (!(mAdapter.getData().size() > 0)){
                    mAdapter.isUseEmpty(true);
                } else {
                    mAdapter.isUseEmpty(false);
                }
            }
        }
    }

    /**
     * 初始化
     * 默认不允许自动加载数据,就是自动刷新
     */
    private void init() {
        if (mSmartRefresh != null){
            mSmartRefresh.autoRefresh();
            //取消内容不满一页时开启上拉加载功能
            mSmartRefresh.setEnableLoadMoreWhenContentNotFull(false);
            //下拉刷新
            mSmartRefresh.setOnRefreshListener(this);
            //上拉加载更多
            mSmartRefresh.setOnLoadMoreListener(this);
        }
        if (getEmptyView() != null){
            mAdapter.setEmptyView(getEmptyView());
            mAdapter.isUseEmpty(false);
        }
    }

    /**
     * 从网络获取数据,这个使用过程中自己实现其中方法
     * @param page 获取第几页的数据
     */
    abstract void getData(int page);

    /**
     * 获取空白页面
     * @return
     */
    abstract View getEmptyView();

    private void refresh(){
        page = 0;
        page++;
        getData(page);
    }

    private void loadMore(){
        page++;
        getData(page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        loadMore();
    }
}
