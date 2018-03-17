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

    public ApiResponse<Void> publishHomework(String title, String deadline, String detail, List<File> photoList, String classId);

    public ApiResponse<List> getHomeworkList(String classId, String userId, String userTitle, String requestStatus);

    public ApiResponse<Void> changeHomeworkStatus(String homeworkId, String homeworkStatus);

    public ApiResponse<HomeworkAnswerWithBLOBs> getStuHomeworkDetail(String homeworkAnswerId);

    public ApiResponse<Void> commitHomework(String homeworkAnswerId, String homeworkId, String classId, String userId, String ifSubmit, String detail, List<File> photoList, String delPhotoesUrl);

    public ApiResponse<HomeworkWithBLOBs> getHomeworkDetail(String homeworkId);

    public ApiResponse<List<HomeworkAnswerWithBLOBs>> getHomeworkAnswerList(String homeworkId);

    public ApiResponse<Void> commitHomeworkEvaluation(String homeworkAnswerId, String exp, String remark, List<File> photoList, String delPhotoesUrl);

    public ApiResponse<Integer> getNotEvaluateStuNum(String homeworkId);
}
