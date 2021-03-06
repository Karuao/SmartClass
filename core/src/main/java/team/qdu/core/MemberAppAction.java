package team.qdu.core;

import java.util.List;

import team.qdu.model.Attendance;
import team.qdu.model.Attendance_user;
import team.qdu.model.ClassUser;
import team.qdu.model.ClassUserExp;

/**
 * Created by asus on 2018/3/7.
 */

public interface MemberAppAction {

    //获取某个班课成员列表
    void getClassMembers(String classId, Lifeful lifeful, ActionCallbackListener<List<ClassUser>> listener);

    //获取登录用户的信息
    ClassUser getMemberInfo(String classUserId, Lifeful lifeful, ActionCallbackListener<ClassUser> listener);

    //获取图片
//    public void getBitmap(String urlTail, ActionCallbackListener<Bitmap> listener);

    //移出班课
    void shiftClass(String classUserId, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //教师端开始签到
    void beginSignInForTeacher(String classId, Lifeful lifeful, ActionCallbackListener<Attendance> listener);

    //教师放弃本次签到
    void giveUpSignIn(String attendanceId, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //教师结束本次签到
    void endSignIn(String attendanceId, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //教师获取签到历史
    void getTeacherSignInHistory(String classId, Lifeful lifeful, ActionCallbackListener<List<Attendance>> listener);

    //学生获取签到历史
    void getStudentSignInHistory(String userId, String classId, Lifeful lifeful, ActionCallbackListener<List<Attendance_user>> listener);

    //学生签到
    void beginSignInForStudent(String userId, String attendanceId, String classUserId, Lifeful lifeful, ActionCallbackListener<Attendance_user> listener);

    //获得签到信息
    void getAttendanceInfo(String classId, Lifeful lifeful, ActionCallbackListener<List<Attendance>> listener);

    //获得经验值明细
    void getExpDetail(String classId, String userId, Lifeful lifeful, ActionCallbackListener<List<ClassUserExp>> listener);

    //获得学生签到信息
    void getAttendanceUserInfo(String attendanceId, Lifeful lifeful, ActionCallbackListener<List<Attendance_user>> listener);

    //教师端设置学生为已签到
    void setStudentSignIn(String attendanceUserId, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //教师端设置学生为未签到
    void setStudentNotSignIn(String attendanceUserId, Lifeful lifeful, ActionCallbackListener<Void> listener);
}
