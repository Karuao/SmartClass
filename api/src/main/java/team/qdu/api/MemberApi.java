package team.qdu.api;

import android.graphics.Bitmap;

import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.ClassUser;

/**
 * Created by asus on 2018/3/7.
 */

public interface MemberApi {

    public ApiResponse<List<ClassUser>> getClassMembers(String classId);

    public Bitmap getBitmap(String urlTail);
}
