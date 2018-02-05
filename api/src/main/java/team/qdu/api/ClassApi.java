package team.qdu.api;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.Class;

/**
 * Created by 11602 on 2018/1/24.
 */

public interface ClassApi {

    public ApiResponse<List<Class>> getJoinedClasses(String userId);

    public ApiResponse<Void> jumpClass(String classId, String userId);

    public Bitmap getBitmap(String urlTail);

    public ApiResponse<String> createClass(File avatar, String name, String course, String userId);

    public ApiResponse<Class> joinClass(String classId, String userId);

    public ApiResponse<Void> confirmJoinClass(String classId, String userId);
}
