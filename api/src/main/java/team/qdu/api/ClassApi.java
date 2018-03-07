package team.qdu.api;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.Class;
import team.qdu.model.ClassUser;

/**
 * Created by 11602 on 2018/1/24.
 */

public interface ClassApi {

    public ApiResponse<List<Class>> getJoinedClasses(String userId);

    public ApiResponse<ClassUser> jumpClass(String classId, String userId);

    public Bitmap getBitmap(String urlTail);

    public ApiResponse<String> createClass(File avatar, String name, String course, String userId);

    public ApiResponse<Class> joinClass(String classId, String userId);

    public ApiResponse<Void> confirmJoinClass(String classId, String userId);

    public ApiResponse<Void> notAllowToJoin(String classId);

    public ApiResponse<Void> allowToJoin(String classId);

    public ApiResponse<Class> searchByClassId(String classId);

    public ApiResponse<Void> finishClass(String classId);

    public ApiResponse<Void> deleteClass(String classId);

    public ApiResponse<String> modifyClass(String classId, File avatar, String className, String course, String university, String department, String goal, String exam);

    public ApiResponse<Void> quitClass(String classId, String userId);

    public Void readNew(String classUserId, String whichPage);
}
