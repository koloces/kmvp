package com.koloces.utilslib.utils.imgPicker;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import com.koloces.utilslib.R;
import com.koloces.utilslib.mvpBase.BaseApplication;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.List;

/**
 * Created by koloces on 2020/3/10
 * 图片选择相关工具(缓存文件清除等)
 */
public class ImagePickerUtils {

    public static void openImg(Activity context,List<LocalMedia> list, int max, boolean showCamera, boolean isShowGif,int requestCode){
        openAlbum(context,PickType.img,list,max,isShowGif,isShowGif,requestCode);
    }
    public static void openVideo(Activity context,List<LocalMedia> list, int max, boolean showCamera, boolean isShowGif,int requestCode){
        openAlbum(context,PickType.video,list,max,isShowGif,isShowGif,requestCode);
    }
    public static void openAudio(Activity context,List<LocalMedia> list, int max, boolean showCamera, boolean isShowGif,int requestCode){
        openAlbum(context,PickType.audio,list,max,isShowGif,isShowGif,requestCode);
    }
    public static void openAll(Activity context,List<LocalMedia> list, int max, boolean showCamera, boolean isShowGif,int requestCode){
        openAlbum(context,PickType.audio,list,max,isShowGif,isShowGif,requestCode);
    }

    /**
     * 打开相机
     * 打开相机
     * 打开相机
     * @param content
     * @param requestCode
     */
    public static void openCamera(Activity content,int requestCode){
        PictureSelector.create(content)
                .openCamera(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(requestCode);
    }

    /**
     * 清除缓存
     */
    public static void cleanCache(){
        //包括裁剪和压缩后的缓存，要在上传成功后调用，type 指的是图片or视频缓存取决于你设置的ofImage或ofVideo 注意：需要系统sd卡权限
        PictureFileUtils.deleteCacheDirFile(BaseApplication.getInstance(),PictureMimeType.ofAll());
        // 清除所有缓存 例如：压缩、裁剪、视频、音频所生成的临时文件
        PictureFileUtils.deleteAllCacheDirFile(BaseApplication.getInstance());
    }

    /**
     * 图片预览(本地图片,选中图片后需要预览)
     * @param activity
     * @param position
     */
    public static void externalImg(Activity activity,int position,List<LocalMedia> selectList){
        PictureSelector.create(activity)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .openExternalPreview(position, selectList);
    }

    /**
     * 视频预览
     * @param activity
     * @param video_path
     */
    public static void sexernalVideo(Activity activity,String video_path){
        PictureSelector.create(activity).externalPictureVideo(video_path);
    }


    /**
     * 打开相册
     * @param context
     * @param type          相册类型
     * @param list          已选择图片
     * @param max           最多选图片
     * @param showCamera    是否显示拍摄按钮
     * @param isShowGif     是否显示gif
     * @param requestCode   回调的code
     */
    public static void openAlbum(Activity context, PickType type, List<LocalMedia> list, int max, boolean showCamera, boolean isShowGif,int requestCode){
        PictureSelector.create(context)
                .openGallery(type == PickType.all ? PictureMimeType.ofAll() : type == PickType.img ? PictureMimeType.ofImage() : type == PickType.video ? PictureMimeType.ofVideo() : PictureMimeType.ofAudio())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
//                .setPictureStyle()// 动态自定义相册主题  注意：此方法最好不要与.theme();同时存在， 二选一
//                .setPictureCropStyle()// 动态自定义裁剪主题
//                .setPictureWindowAnimationStyle()// 自定义相册启动退出动画
                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项   参考Demo MainActivity中代码
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isUseCustomCamera(false)// 是否使用自定义相机，5.0以下请不要使用，可能会出现兼容性问题
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(true)// 是否显示原图控制按钮，如果用户勾选了 压缩、裁剪功能将会失效
                .isWeChatStyle(false)// 是否开启微信图片选择风格，此开关开启了才可使用微信主题！！！
                .isAndroidQTransform(true)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && enableCrop(false);有效
//                .bindCustomPlayVideoCallback(callback)// 自定义播放回调控制，用户可以使用自己的视频播放界面
//                .isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
//                .isMultipleRecyclerAnimation(false)// 多图裁剪底部列表显示动画效果
//                .setLanguage(language)// 设置语言，默认中文
                .maxSelectNum(max)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .minVideoSelectNum(1)// 视频最小选择数量，如果没有单独设置的需求则可以不设置，同用minSelectNum字段
                .maxVideoSelectNum(max) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
                .imageSpanCount(4)// 每行显示个数 int
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                .queryMaxFileSize(100)// 只查多少M以内的图片、视频、音频  单位M
                .querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                .cameraFileName(System.currentTimeMillis() + ".png") // 重命名拍照文件名、注意这个只在使用相机时可以使用
                .renameCompressFile(System.currentTimeMillis() + ".png")// 重命名压缩文件名、 注意这个不要重复，只适用于单张图压缩使用
                .renameCropFileName(System.currentTimeMillis() + ".png")// 重命名裁剪文件名、 注意这个不要重复，只适用于单张图裁剪使用
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
//                .setTitleBarBackgroundColor()//相册标题栏背景色
//                .isChangeStatusBarFontColor()// 是否关闭白色状态栏字体颜色
//                .setStatusBarColorPrimaryDark()// 状态栏背景色
//                .setUpArrowDrawable()// 设置标题栏右侧箭头图标
//                .setDownArrowDrawable()// 设置标题栏右侧箭头图标
//                .isOpenStyleCheckNumMode()// 是否开启数字选择模式 类似QQ相册
                .selectionMode(max == 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE  )// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(showCamera)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(false)// 是否裁剪 true or false
//                .setCircleDimmedColor()// 设置圆形裁剪背景色值
//                .setCircleDimmedBorderColor()// 设置圆形裁剪边框色值
//                .setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(isShowGif)// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
//                .freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
//                .circleDimmedLayer()// 是否圆形裁剪 true or false
//                .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .selectionMedia(list)// 是否传入已选图片 List<LocalMedia> list
//                .previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality(90)// 废弃 改用cutOutQuality()
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                .rotateEnabled() // 裁剪是否可旋转图片 true or false
//                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
                .videoQuality(1)// 视频录制质量 0 or 1 int
                .videoMaxSecond(60)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(360)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(60)//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(requestCode);//结果回调onActivityResult code
        //.forResult(OnResultCallbackListener listener);//Callback回调方式
    }

    /**
     * 图片选择回调,在onActivityResult中调用
     * @param requestCode
     * @param data
     */
    public static void onResult(int requestCode,Intent data,OnPickerResultListener listener ){
        if (listener == null)return;
        List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
        if (localMedia == null)return;
        if (localMedia.size() == 0)return;
        listener.onSuccess(requestCode,localMedia);
    }
}
