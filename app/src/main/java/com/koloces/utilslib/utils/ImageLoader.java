package com.koloces.utilslib.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.koloces.utilslib.R;

/**
 * Created by koloces on 2020/3/10
 * 图片加载工具
 */
public class ImageLoader {
    public static void load(Context context, String imgPath, ImageView iv,int placeHolder,int errorImage){
        if (context == null) return ;
        if (imgPath == null) return ;
        if (iv == null) return ;
        Glide.with(context)
                .load(imgPath)
                .apply(getLoadRequestOptions(placeHolder, errorImage))
                .into(iv);
    }

    public static void load(Context context,String imgPath,ImageView iv){
        load(context,imgPath,iv, R.drawable.picture_image_placeholder,R.drawable.picture_icon_data_error);
    }

    /**
     * 获取加载图片的公共参数
     *
     * @return
     */
    public static RequestOptions getLoadRequestOptions(int placeHolder,int error) {
        return new RequestOptions()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeHolder)
                .error(error);
    }
}
