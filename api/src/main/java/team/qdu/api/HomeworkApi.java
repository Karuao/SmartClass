package team.qdu.api;

import java.io.File;

import team.qdu.model.ApiResponse;

/**
 * Created by 11602 on 2018/2/8.
 */

public interface HomeworkApi {

    public ApiResponse<Void> publishHomework(String title, String deadline, String detail, File photo, String classId);
}
