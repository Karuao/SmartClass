package team.qdu.api;

import java.io.File;
import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.HomeworkAnswerWithBLOBs;

/**
 * Created by 11602 on 2018/2/8.
 */

public interface HomeworkApi {

    public ApiResponse<Void> publishHomework(String title, String deadline, String detail, File photo, String classId);

    public ApiResponse<List> getHomeworkList(String classId, String userId, String userTitle, String requestStatus);

    public ApiResponse<Void> changeHomeworkStatus(String homeworkId, String homeworkStatus);

    public ApiResponse<HomeworkAnswerWithBLOBs> getStuHomeworkDetail(String homeworkAnswerId);

    public ApiResponse<Void> submitHomework(String homeworkAnswerId, String detail, File answerPhoto);
}
