package com.koloces.utilslib.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.koloces.utilslib.R;
import com.koloces.utilslib.utils.StringUtil;


/**
 * Created by koloces on 2019/4/13
 */
public class LoadingDialog extends BaseDialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
        getWindow().setWindowAnimations(R.style.LoadingDialogWindowStyle);
    }

    @Override
    protected int getLayout() {
        return R.layout.loading_alert_dialog;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView() {

    }

    public void setText(String str) {
        TextView tv = findViewById(R.id.text);
        if (StringUtil.isEmpty(str)) {
            tv.setVisibility(View.GONE);
            return;
        }
        tv.setVisibility(View.VISIBLE);
        tv.setText(str);
    }
}
