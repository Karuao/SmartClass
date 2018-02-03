package team.qdu.smartclass.activity;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import team.qdu.core.ClassAppAction;
import team.qdu.core.UserAppAction;
import team.qdu.smartclass.SApplication;

/**
 * Activity抽象基类
 * <p>
 * Created by Rock on 2017/4/23.
 */

public abstract class SBaseActivity extends AppCompatActivity {
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

    //从SharedPreferences获取userId
    public String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        return  sharedPreferences.getString("userId", null);
    }

    public void setUserId(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("classId", userId);
        editor.commit();
    }

    //从SharedPreferences获取userId
    public String getClassId() {
        SharedPreferences sharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        return  sharedPreferences.getString("classId", null);
    }

    public void setClassId(String classId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("classId", classId);
        editor.commit();
    }
}
