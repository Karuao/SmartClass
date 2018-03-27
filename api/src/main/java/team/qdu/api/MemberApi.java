package team.qdu.api;

import android.graphics.Bitmap;

import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.Attendance;
import team.qdu.model.Attendance_user;
import team.qdu.model.ClassUser;

/**
 * Created by asus on 2018/3/7.
 */

public interface MemberApi {

    public ApiResponse<List<ClassUser>> getClassMembers(String classId);

    public Bitmap getBitmap(String urlTail);

    public ApiResponse<ClassUser> searchByClassUserId(String classUserId);

    public ApiResponse<List<Attendance>> getAttendanceInfo(String classId);

    public ApiResponse<List<Attendance_user>> getAttendanceUserInfo(String attendanceId);

    public ApiResponse<Void> shiftClass(String classUserId);

    public ApiResponse<List<Attendance_user>> setStudentSignIn(String attendanceUserId);

    public ApiResponse<List<Attendance_user>> setStudentNotSignIn(String attendanceUserId);

    public ApiResponse<Attendance_user> beginSignInForStudent(String userId, String attendanceId,String classUserId);

    public ApiResponse<Attendance> beginSignInForTeacher(String classId);

    public ApiResponse<Void> giveUpSignIn(String attendanceId);

    public ApiResponse<Void> endSignIn(String attendanceId);

    public ApiResponse<List<Attendance>> getTeacherSignInHistory(String classId);

    public ApiResponse<List<Attendance_user>> getStudentSignInHistory(String userId,String classId);
}
