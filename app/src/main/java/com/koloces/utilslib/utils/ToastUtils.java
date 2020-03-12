package com.koloces.utilslib.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.koloces.utilslib.mvpBase.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koloces on 2020/3/10
 * 吐司工具
 */
public class ToastUtils {
    private static ArrayList<String> notToast;
    private static Toast mToast;
    static {
        notToast = new ArrayList<>();
    }

    /**
     * 土司
     * @param str
     */
    @SuppressLint("ShowToast")
    public static void toast(String str){
        if (StringUtil.isEmpty(str)){
            return;
        }
        for (String s : notToast) {
            if (str.equals(s)){
                return;
            }
        }
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getInstance(), str, Toast.LENGTH_SHORT);
        }
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 添加不用显示的吐司的文字(有时候可能有用)
     * @param notToastStrs
     */
    public static void putNotToastStr(List<String> notToastStrs){
        if (notToastStrs == null || notToastStrs.size() == 0)return;
        notToast.clear();
        notToast.addAll(notToastStrs);
    }
}
