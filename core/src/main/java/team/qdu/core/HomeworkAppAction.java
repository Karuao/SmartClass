package team.qdu.core;

import java.io.File;
import java.util.List;

import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.model.HomeworkWithBLOBs;

/**
 * Created by 11602 on 2018/2/8.
 */

public interface HomeworkAppAction {

    //上传新发布作业的信息
    public void publishHomework(String title, String deadline, String detail, List<File> photoList, String classId, ActionCallbackListener<Void> listener);

    //获取作业列表
    public void getHomeworkList(String classId, String userId, String userTitle, String requestStatus, ActionCallbackListener<List> listener);

    //更改作业状态
    public void changeHomeworkStatus(String homeworkId, String homeworkStatus, String homeworkTitle, ActionCallbackListener<Void> listener);

    //获取学生作业详情
    public void getStuHomeworkDetail(String homeworkAnswerId, ActionCallbackListener<HomeworkAnswerWithBLOBs> listener);

    //学生提交作业
    public void commitHomework(String homeworkAnswerId, String homeworkId, String classId, String userId, String ifSubmit, String homeworkTitle, String detail, List<File> photoList, String delPhotoesUrl, ActionCallbackListener<Void> listener);

    //获取作业详情
    public void getHomeworkDetail(String homeworkId, ActionCallbackListener<HomeworkWithBLOBs> listener);

    //获取某作业学生提交情况List
    public void getHomeworkAnswerList(String homeworkId, ActionCallbackListener<List<HomeworkAnswerWithBLOBs>> listener);

    //提交作业评价
    public void commitHomeworkEvaluation(String homeworkAnswerId, String exp, String remark, List<File> photoList, String delPhotoesUrl, ActionCallbackListener<Void> listener);

    //获取作业未评价的学生人数
    public void getNotEvaluateStuNum(String homeworkId, ActionCallbackListener<Integer> listener);
}
