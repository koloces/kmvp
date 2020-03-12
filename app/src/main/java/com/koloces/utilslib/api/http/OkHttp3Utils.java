package com.koloces.utilslib.api.http;

import com.koloces.utilslib.mvpBase.BaseApplication;
import com.koloces.utilslib.utils.LogUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by koloces on 2020/3/10
 */
public class OkHttp3Utils {
    private static OkHttpClient mOkHttpClient;
    //设置缓存目录
    private static File cacheDirectory;
    private static Cache cache;

    public static void init() {
        cacheDirectory = new File(BaseApplication.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "MyHttpCache");
        cache = new Cache(cacheDirectory, 10 * 1024 * 1024);
    }

    /**
     * 获取OkHttpClient对象
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        if (null == mOkHttpClient) {
            synchronized (OkHttp3Utils.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = createHttpClient();
                }
            }
        }
        return mOkHttpClient;
    }

    private static OkHttpClient createHttpClient() {
        //同样okhttp3后也使用build设计模式
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("http", message);
            }
        });
        //log打印级别，决定了log显示的详细程度
        logInterceptor.setLevel(BaseApplication.getInstance().isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient OkHttpClient = new OkHttpClient.Builder()
                //添加拦截器
//                    .addInterceptor(new HttpLoggingInterceptor(BaseApplication.getInstance().isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .addInterceptor(logInterceptor)
                .addInterceptor(new MyIntercepter())
                //设置一个自动管理cookies的管理器
                .cookieJar(new CookiesManager())
                //添加网络连接器
//                    .addNetworkInterceptor(new CookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                //设置请求读写的超时时间
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(cache)//设置缓存
                .retryOnConnectionFailure(true)//自动重试
                .build();
        return OkHttpClient;
    }
}
