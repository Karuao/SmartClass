package team.qdu.core;

import java.io.File;
import java.util.List;

import team.qdu.model.Class;
import team.qdu.model.ClassUser;

/**
 * Created by 11602 on 2018/1/24.
 */

public interface ClassAppAction {

    //获取登录用户加入的班课列表
    public void getJoinedClasses(String userId, Lifeful lifeful, ActionCallbackListener<List<ClassUser>> listener);

    //跳转老师班课/学生班课
    public void jumpClass(String classId, String userId, Lifeful lifeful, ActionCallbackListener<ClassUser> listener);

    //获取图片
//    public void getBitmap(String urlTail, Lifeful lifeful, ActionCallbackListener<Bitmap> listener);

    //不允许加入班课
    public void notAllowToJoin(String classId, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //允许加入班课
    public void allowToJoin(String classId, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //获得课程信息
    public Class getClassInfor(String classId, Lifeful lifeful, ActionCallbackListener<Class> listener);

    //结束班课
    public void finishClass(String classId, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //删除班课
    public void deleteClass(String classId, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //编辑班课
    public void compileClass(String classId, File avatar, String className, String course, String university, String department, String goal, String exam, Lifeful lifeful, ActionCallbackListener<String> listener);

    //创建班课
    public void createClass(File avatar, String name, String course, String userId, Lifeful lifeful, ActionCallbackListener<ClassUser> listener);

    //加入班课
    public void joinClass(String classId, String userId, Lifeful lifeful, ActionCallbackListener<Class> listener);

    //确认加入班课
    public void confirmJoinClass(String classId, String userId, Lifeful lifeful, ActionCallbackListener<Integer> listener);

    //退出班课
    public void quitClass(String classId, String userId, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //查看有新推送的页面后进行操作
    public void readNew(String classUserId, String whichPage);
}
