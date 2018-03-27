package team.qdu.core;

import android.graphics.Bitmap;

import java.util.List;

import team.qdu.model.Attendance;
import team.qdu.model.Attendance_user;
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

    //学生获取签到历史
    public void getStudentSignInHistory(String userId,String classId,ActionCallbackListener<List<Attendance_user>> listener);

    //学生签到
    public void beginSignInForStudent(String userId,String attendanceId,String classUserId,ActionCallbackListener<Attendance_user> listener);

    //获得签到信息
    public void getAttendanceInfo(String classId,ActionCallbackListener<List<Attendance>> listener);

    //获得学生签到信息
    public void getAttendanceUserInfo(String attendanceId,ActionCallbackListener<List<Attendance_user>> listener);

    //教师端设置学生为已签到
    public void setStudentSignIn(String attendanceUserId,ActionCallbackListener<List<Attendance_user>> listener);

    //教师端设置学生为未签到
    public void setStudentNotSignIn(String attendanceUserId,ActionCallbackListener<List<Attendance_user>> listener);
}
