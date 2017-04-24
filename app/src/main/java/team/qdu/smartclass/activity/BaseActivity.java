package team.qdu.smartclass.activity;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.FragmentActivity;

import team.qdu.smartclass.SApplication;

/**
 * Activity抽象基类
 *
 * Created by Rock on 2017/4/23.
 */

public abstract class BaseActivity extends Activity {
    // 上下文实例
    public Context context;
    // 应用全局的实例
    public SApplication application;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        context = getApplicationContext();
        application = (SApplication) this.getApplication();
    }
}
