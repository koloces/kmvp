package com.koloces.utilslib.mvpBase.example;

import com.koloces.utilslib.api.http.RetrofitUtils;
import com.koloces.utilslib.mvpBase.BaseApplication;

/**
 * Created by koloces on 2020/3/12
 */
public class TestApp extends BaseApplication {
    @Override
    protected void initApp() {
        RetrofitUtils.putBaseApi("http://server.pengpengspace.cn/");
    }

    @Override
    public boolean isDebug() {
        return true;
    }
}
