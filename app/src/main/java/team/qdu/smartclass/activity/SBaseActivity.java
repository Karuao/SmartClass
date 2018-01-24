package team.qdu.smartclass.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import team.qdu.core.ClassAppAction;
import team.qdu.core.UserAppAction;
import team.qdu.smartclass.SApplication;

/**
 * Activity抽象基类
 * <p>
 * Created by Rock on 2017/4/23.
 */

public abstract class SBaseActivity extends FragmentActivity {
    //上下文实例
    public Context context;

    //应用全局的实例
    public SApplication application;

    //核心层的Action实例
    public UserAppAction userAppAction;

    public ClassAppAction classAppAction;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        context = getApplicationContext();
        application = (SApplication) this.getApplication();
        userAppAction = application.getUserAppAction();
        classAppAction = application.getClassAppAction();
    }
}
