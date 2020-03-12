package com.koloces.utilslib.mvpBase.example;

import com.koloces.utilslib.mvpBase.BaseView;

/**
 * Created by koloces on 2020/3/12
 * MVP 例子 的登录界面
 * 这三个获取方法可以不用谢,这只是个例子,在应用中直接从activity控件中获取也行
 *
 */
public interface TestView extends BaseView {
    /**
     * 获取密码
     * @return
     */
    String getPsw();

    /**
     * 获取电话号
     * @return
     */
    String getPhone();

    /**
     * 是否同意协议
     * @return
     */
    boolean isAgreement();

    /**
     * 登录成功(还有去注册,忘记密码等,这里就不写了,类似一样的写法多来几个)
     */
    void loginSuccess();

}
