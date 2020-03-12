package com.koloces.utilslib.api.http;

import android.util.ArrayMap;

/**
 * Created by koloces on 2020/3/10
 * 添加公共请求参数
 *
 */
public class HttpPublicParams {
    public static ArrayMap<String,String> publicHeader = new ArrayMap<>();
    public static ArrayMap<String,String> publicBodys = new ArrayMap<>();

    public static void putHeader(String key,String value){
        publicHeader.put(key,value);
    }
    public static void putBody(String key,String value){
        publicBodys.put(key,value);
    }
}
