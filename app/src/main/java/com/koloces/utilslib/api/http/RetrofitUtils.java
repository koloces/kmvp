package com.koloces.utilslib.api.http;

import com.koloces.utilslib.utils.StringUtil;
import com.koloces.utilslib.utils.ToastUtils;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by koloces on 2020/3/10
 * 使用前必须先配置Service
 */
public class RetrofitUtils {
    private static HashMap<String, Retrofit> retorfitMap = new HashMap<>();
    private static String mainService;

    /**
     * 防止可能有多个BaseUr
     * 第一个baseService是住要的,{@link RetrofitUtils}getRetrofit方法会返回这个主要的serivce的对象
     * @param baseService
     */
    public static void putBaseApi(String baseService){
        if (StringUtil.isEmpty(mainService)){
            mainService = baseService;
        }
        Retrofit retrofit = retorfitMap.get(baseService);
        if (retrofit != null)return;
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(baseService)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttp3Utils.getOkHttpClient())
                .build();
        retorfitMap.put(baseService,retrofit1);
    }

    /**
     * 获取默认的
     * @return
     */
    public static Retrofit getRetrofit(){
        return getRetrofit(mainService);
    }

    /**
     * 根据baseService获取
     * @param baseService
     * @return
     */
    public static Retrofit getRetrofit(String baseService){
        Retrofit retrofit = retorfitMap.get(baseService);
        if (retrofit == null){
            ToastUtils.toast("请先配置Service");
        }
        return retrofit;
    }
}
