package team.qdu.api;

import java.io.File;
import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.model.HomeworkWithBLOBs;

/**
 * Created by 11602 on 2018/2/8.
 */

public interface HomeworkApi {

    ApiResponse<Void> publishHomework(String title, String deadline, String detail, List<File> photoList, String classId);

    ApiResponse<List> getHomeworkList(String classId, String userId, String userTitle, String requestStatus);

    ApiResponse<Void> changeHomeworkStatus(String homeworkId, String homeworkStatus, String homeworkTitle);

    ApiResponse<HomeworkAnswerWithBLOBs> getStuHomeworkDetail(String homeworkAnswerId);

    ApiResponse<Void> commitHomework(String homeworkAnswerId, String homeworkId, String classId, String userId, String ifSubmit, String homeworkTitle, String detail, List<File> photoList, String delPhotoesUrl);

    ApiResponse<HomeworkWithBLOBs> getHomeworkDetail(String homeworkId);

    ApiResponse<List<HomeworkAnswerWithBLOBs>> getHomeworkAnswerList(String homeworkId);

    ApiResponse<Void> commitHomeworkEvaluation(String homeworkAnswerId, String exp, String remark, List<File> photoList, String delPhotoesUrl);

    ApiResponse<Integer> getNotEvaluateStuNum(String homeworkId);
}
