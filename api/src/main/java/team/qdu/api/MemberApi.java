package team.qdu.api;

import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.Attendance;
import team.qdu.model.Attendance_user;
import team.qdu.model.ClassUser;
import team.qdu.model.ClassUserExp;

/**
 * Created by asus on 2018/3/7.
 */

public interface MemberApi {

    ApiResponse<List<ClassUser>> getClassMembers(String classId);

    ApiResponse<ClassUser> searchByClassUserId(String classUserId);

    ApiResponse<List<Attendance>> getAttendanceInfo(String classId);

    ApiResponse<List<Attendance_user>> getAttendanceUserInfo(String attendanceId);

    ApiResponse<List<ClassUserExp>> getExpDetail(String classId, String userId);

    ApiResponse<Void> shiftClass(String classUserId);

    ApiResponse<Void> setStudentSignIn(String attendanceUserId);

    ApiResponse<Void> setStudentNotSignIn(String attendanceUserId);

    ApiResponse<Attendance_user> beginSignInForStudent(String userId, String attendanceId, String classUserId);

    ApiResponse<Attendance> beginSignInForTeacher(String classId);

    ApiResponse<Void> giveUpSignIn(String attendanceId);

    ApiResponse<Void> endSignIn(String attendanceId);

    ApiResponse<List<Attendance>> getTeacherSignInHistory(String classId);

    ApiResponse<List<Attendance_user>> getStudentSignInHistory(String userId, String classId);
}
