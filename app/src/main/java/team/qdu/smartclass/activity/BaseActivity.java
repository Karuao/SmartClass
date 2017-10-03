package team.qdu.smartclass.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import team.qdu.core.AppAction;
import team.qdu.smartclass.SApplication;

/**
 * Activity抽象基类
 *
 * Created by Rock on 2017/4/23.
 */

public abstract class BaseActivity extends Activity {
    //上下文实例
    public Context context;
    //应用全局的实例
    public SApplication application;
    //核心层的Action实例
    public AppAction appAction;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        context = getApplicationContext();
        application = (SApplication) this.getApplication();
        appAction = application.getAppAction();
    }
}
