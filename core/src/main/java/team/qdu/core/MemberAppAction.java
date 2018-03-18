package team.qdu.core;

import android.graphics.Bitmap;

import java.util.List;

import team.qdu.model.Attendance;
import team.qdu.model.ClassUser;

/**
 * Created by asus on 2018/3/7.
 */

public interface MemberAppAction {

    //获取某个班课成员列表
    public void getClassMembers(String classId, ActionCallbackListener<List<ClassUser>> listener);

    //获取登录用户的信息
    public ClassUser getMemberInfo(String classUserId, ActionCallbackListener<ClassUser> listener);

    //获取图片
    public void getBitmap(String urlTail, ActionCallbackListener<Bitmap> listener);

    //移出班课
    public void shiftClass(String classUserId,ActionCallbackListener<Void> listener);

    //教师端开始签到
    public void beginSignInForTeacher(String classId,ActionCallbackListener<Attendance> listener);

    //教师放弃本次签到
    public void giveUpSignIn(String attendanceId,ActionCallbackListener<Void> listener);

    //教师结束本次签到
    public void endSignIn(String attendanceId,ActionCallbackListener<Void> listener);

    //教师获取签到历史
    public void getTeacherSignInHistory(String classId,ActionCallbackListener<List<Attendance>> listener);
}
