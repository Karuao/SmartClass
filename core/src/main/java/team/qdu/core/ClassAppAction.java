package team.qdu.core;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

import team.qdu.model.Class;

/**
 * Created by 11602 on 2018/1/24.
 */

public interface ClassAppAction {

    //获取登录用户加入的班课列表
    public void getJoinedClasses(String userId, ActionCallbackListener<List<Class>> listener);

    //跳转老师班课/学生班课
    public void jumpClass(String classId, String userId, ActionCallbackListener<Void> listener);

    //获取图片
    public void getBitmap(String urlTail, ActionCallbackListener<Bitmap> listener);

    //创建班课
    public void createClass(File avatar, String name, String course, String userId, ActionCallbackListener<String> listener);

    //加入班课
    public void joinClass(String classId, String userId, ActionCallbackListener<Class> listener);

    //确认加入班课
    public void confirmJoinClass(String classId, String userId, ActionCallbackListener<Void> listener);
}
