package com.koloces.utilslib.api.http;

import android.util.ArrayMap;

import com.koloces.utilslib.api.http.cookie.PersistentCookieStore;
import com.koloces.utilslib.mvpBase.BaseApplication;

import java.lang.ref.SoftReference;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by koloces on 2020/3/10
 */
public class CookiesManager implements CookieJar {
    private final PersistentCookieStore cooks = new PersistentCookieStore(BaseApplication.getInstance().getApplicationContext());
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cooks.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cooks.get(url);
    }
}
