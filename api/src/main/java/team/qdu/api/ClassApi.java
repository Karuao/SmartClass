package team.qdu.api;

import java.io.File;
import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.Class;
import team.qdu.model.ClassUser;

/**
 * Created by 11602 on 2018/1/24.
 */

public interface ClassApi {

    ApiResponse<List<ClassUser>> getJoinedClasses(String userId);

    ApiResponse<ClassUser> jumpClass(String classId, String userId);

    ApiResponse<ClassUser> createClass(File avatar, String name, String course, String userId);

    ApiResponse<Class> joinClass(String classId, String userId);

    ApiResponse<Integer> confirmJoinClass(String classId, String userId);

    ApiResponse<Void> notAllowToJoin(String classId);

    ApiResponse<Void> allowToJoin(String classId);

    ApiResponse<Class> searchByClassId(String classId);

    ApiResponse<Void> finishClass(String classId);

    ApiResponse<Void> deleteClass(String classId);

    ApiResponse<String> modifyClass(String classId, File avatar, String className, String course, String university, String department, String goal, String exam);

    ApiResponse<Void> quitClass(String classId, String userId);

    Void readNew(String classUserId, String whichPage);

}
