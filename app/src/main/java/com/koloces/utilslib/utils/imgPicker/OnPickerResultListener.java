package com.koloces.utilslib.utils.imgPicker;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by koloces on 2020/3/10
 */
public interface OnPickerResultListener {
    void onSuccess(int requestCode,List<LocalMedia> result);
}
