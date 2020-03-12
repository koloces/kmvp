package com.koloces.utilslib.api.example;

import com.koloces.utilslib.api.http.OnHttpResultListener;
import com.koloces.utilslib.api.http.RetrofitUtils;
import com.koloces.utilslib.mvpBase.example.UtilsMainActivity;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by koloces on 2020/3/10
 * 例子:网络请求的例子(包含Json格式请求)
 * 例如登录
 * 例:{@link UtilsMainActivity}
 */
public class TestNetWorkLogin extends TestBaseNetWork{
    NetService netService = RetrofitUtils.getRetrofit().create(NetService.class);
    /**
     * 先声明
     */
    private interface NetService {
        //获取首页轮播图 String可以是任何类型
        @POST("api/user/mobilelogin")
        Observable<TestBean> toLogin(@Body HashMap<String, Object> params);

        @POST("api/AppPubilc/get_lunbotu")
        Observable<TestBean> toRegister(@QueryMap HashMap<String, Object> params);

        /**
         * 请求格式为json的例子
         * @body 即非表单请求体，被@Body注解的ask将会被Gson转换成json发送到服务器
         * @param params
         * @return
         */
        @POST("api/AppPubilc/get_lunbotu")
        Observable<TestBean> toRegister2(@Body HashMap<String, Object> params);
    }

    /**
     * 获取轮播图
     */
    public void login(HashMap<String, Object> params ,OnHttpResultListener<TestBean> observer){
        setSubscribe(netService.toLogin(params), observer);
    }

    public void register(HashMap<String,Object> params,OnHttpResultListener<TestBean> observer){
        setSubscribe(netService.toRegister(params), observer);
    }
}
