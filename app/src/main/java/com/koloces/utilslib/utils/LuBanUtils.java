package com.koloces.utilslib.utils;

import android.content.Context;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by koloces on 2020/3/12
 * 鲁班图片压缩工具
 */
public class LuBanUtils {
    /**
     * 鲁班图片压缩,压缩多张图片
     * @param context
     * @param photos
     * @return
     */
    public static Flowable<List<File>> compress(Context context,List<String> photos){
        return Flowable.just(photos)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        // 同步方法直接返回压缩后的文件
                        return Luban.with(context).load(list).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 压缩单张图片
     * @param context
     * @param photo
     * @return
     */
    public static Flowable<File> compress(Context context,String photo){
        return Flowable.just(photo)
                .observeOn(Schedulers.io())
                .map(new Function<String, File>() {
                    @Override
                    public File apply(String s) throws Exception {
                        List<File> files = Luban.with(context).load(s).get();
                        return files.get(0);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
