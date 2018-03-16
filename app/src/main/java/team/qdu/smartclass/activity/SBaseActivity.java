package team.qdu.smartclass.activity;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lzy.imagepicker.ImagePicker;

import java.util.List;

import team.qdu.core.ClassAppAction;
import team.qdu.core.HomeworkAppAction;
import team.qdu.core.ImgAppAction;
import team.qdu.core.InformAppAction;
import team.qdu.core.MemberAppAction;
import team.qdu.core.UserAppAction;
import team.qdu.smartclass.R;
import team.qdu.smartclass.SApplication;
import team.qdu.smartclass.view.SelectDialog;

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

    public InformAppAction informAppAction;

    public HomeworkAppAction homeworkAppAction;

    public MemberAppAction memberAppAction;

    public ImgAppAction imgAppAction;

    public ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        context = getApplicationContext();
        application = (SApplication) this.getApplication();
        userAppAction = application.getUserAppAction();
        classAppAction = application.getClassAppAction();
        informAppAction = application.getInformAppAction();
        homeworkAppAction = application.getHomeworkAppAction();
        memberAppAction = application.getMemberAppAction();
        imgAppAction = application.getImgAppAction();
        imagePicker = application.getImagePicker();
    }

    //从SharedPreferences获取userId
    public String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        return sharedPreferences.getString("userId", null);
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
        return sharedPreferences.getString("classId", null);
    }

    public void setClassId(String classId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("classId", classId);
        editor.commit();
    }

    //从SharedPreferences获取title老师/学生
    public String getUserTitle() {
        SharedPreferences sharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        return sharedPreferences.getString("userTitle", null);
    }

    public void setUserTitle(String userTitle) {
        SharedPreferences sharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userTitle", userTitle);
        editor.commit();
    }

    //从SharedPreferences获取classUserId
    public String getClassUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        return sharedPreferences.getString("classUserId", null);
    }

    public void setClassUserId(String classUserId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("classUserId", classUserId);
        editor.commit();
    }

    //弹出Dialog
    public SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    public void toBack(View view) {
        finish();
    }
}
