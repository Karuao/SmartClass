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
    public void pushHomework(String title, String deadline, String detail, File photo, String classId, ActionCallbackListener<Void> listener);

    //获取班课列表
    public void getHomeworkList(String classId, String userId, String userTitle, String requestStatus, ActionCallbackListener<List> listener);

    //更改作业状态
    public void changeHomeworkStatus(String homeworkId, String homeworkStatus, ActionCallbackListener<Void> listener);

    //获取学生作业详情
    public void getStuHomeworkDetail(String homeworkAnswerId, ActionCallbackListener<HomeworkAnswerWithBLOBs> listener);

    //学生提交作业
    public void submitHomework(String homeworkAnswerId, String detail, File answerPhoto, ActionCallbackListener<Void> listener);

    //获取作业详情
    public void getHomeworkDetail(String homeworkId, ActionCallbackListener<HomeworkWithBLOBs> listener);
}
