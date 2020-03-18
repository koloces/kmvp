package com.koloces.utilslib.mvpBase.example;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.koloces.utilslib.R;
import com.koloces.utilslib.adapter.FragmentAdapter;
import com.koloces.utilslib.mvpBase.UIActivity;
import com.koloces.utilslib.mvpBase.BasePresenter;

import java.util.Collections;
import java.util.List;

/**
 * Created by koloces on 2020/3/12
 */
public class TestFragmentActivity extends UIActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment_activity;
    }

    @Override
    protected void initView() {
        ViewPager vp = findViewById(R.id.viewPager);
        List<Fragment> fragments = Collections.singletonList(new TestLoginFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),fragments);
        vp.setAdapter(adapter);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
