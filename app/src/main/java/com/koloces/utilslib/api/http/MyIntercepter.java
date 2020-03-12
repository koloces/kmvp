package com.koloces.utilslib.api.http;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.webkit.WebSettings;

import androidx.annotation.RequiresPermission;

import com.koloces.utilslib.mvpBase.BaseApplication;
import com.koloces.utilslib.utils.LogUtils;
import com.koloces.utilslib.utils.ToastUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by koloces on 2020/3/10
 * 自定义拦截器
 */
public class MyIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isNetworkReachable(BaseApplication.getInstance())) {
            ToastUtils.toast("暂无网络");
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                    .build();
        }
        Request.Builder RequestBuilder = request.newBuilder();
        Request build;

        Request.Builder authorization = RequestBuilder
                .method(request.method(),request.body())
                .removeHeader("User-Agent")
                .addHeader("User-Agent", getUserAgent());

        //添加公共请求头
        Iterator<Map.Entry<String, String>> headerIterator = HttpPublicParams.publicHeader.entrySet().iterator();
        while (headerIterator.hasNext()){
            Map.Entry<String, String> next = headerIterator.next();
            String key = next.getKey();
            String value = next.getValue();
            authorization.addHeader(key,value);
        }
        //添加公共请求体
        Iterator<Map.Entry<String, String>> bodyIterator = HttpPublicParams.publicBodys.entrySet().iterator();
        HttpUrl.Builder bodyBuilder = request.url().newBuilder();
        while (bodyIterator.hasNext()){
            Map.Entry<String, String> next = bodyIterator.next();
            String key = next.getKey();
            String value = next.getValue();
            bodyBuilder.addQueryParameter(key,value);
        }
        HttpUrl httpUrl = bodyBuilder.build();

        build = authorization.url(httpUrl).build();
        return chain.proceed(build);
    }

    private static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(BaseApplication.getInstance().getApplicationContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
