package com.koloces.utilslib.mvpBase.example;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.koloces.utilslib.R;
import com.koloces.utilslib.api.example.TestBean;
import com.koloces.utilslib.api.example.TestNetWorkHome;
import com.koloces.utilslib.api.example.TestNetWorkLogin;
import com.koloces.utilslib.api.http.OnHttpResultListener;
import com.koloces.utilslib.utils.ToastUtils;
import com.koloces.utilslib.utils.activity.ActivityUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class UtilsMainActivity extends AppCompatActivity {
    private List<LocalMedia> imgs;
    private TestNetWorkHome homeNet;
    private TestNetWorkLogin loginNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityUtils.getInstance().addActivity(this);

//        loginNet = new TestNetWorkLogin();
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testNet();
            }
        });

    }

    private void testNet() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("mobile", "18223608751");
        map.put("captcha", "123456");
        loginNet.login(map, new OnHttpResultListener<TestBean>() {
            @Override
            public void onSuccess(TestBean result) {
                ToastUtils.toast(result.data.im_token);
            }

            @Override
            public void onFailed(int errCode, String msg) {
                ToastUtils.toast(msg);
            }

            @Override
            public void getDisposable(Disposable disposable) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        ActivityUtils.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
