package team.qdu.core;

import java.util.List;

import team.qdu.model.HomeworkAnswerWithBLOBs;
import team.qdu.model.HomeworkWithBLOBs;

/**
 * Created by 11602 on 2018/2/8.
 */

public interface HomeworkAppAction {

    //上传新发布作业的信息
//    void publishHomework(String title, String deadline, String detail, List<File> photoList, String classId, Lifeful lifeful, ActionCallbackListener<Void> listener);
    void publishHomework(String title, String deadline, String detail, List<String> imgPahtList, String classId, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //获取作业列表
    void getHomeworkList(String classId, String userId, String userTitle, String requestStatus, Lifeful lifeful, ActionCallbackListener<List> listener);

    //更改作业状态
    void changeHomeworkStatus(String homeworkId, String homeworkStatus, String homeworkTitle, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //获取学生作业详情
    void getStuHomeworkDetail(String homeworkAnswerId, Lifeful lifeful, ActionCallbackListener<HomeworkAnswerWithBLOBs> listener);

    //学生提交作业
    void commitHomework(String homeworkAnswerId, String homeworkId, String classId, String userId, String ifSubmit, String homeworkTitle, String detail, List<String> imgPathList, String delPhotoesUrl, String ifChangePhotoes, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //获取作业详情
    void getHomeworkDetail(String homeworkId, Lifeful lifeful, ActionCallbackListener<HomeworkWithBLOBs> listener);

    //获取某作业学生提交情况List
    void getHomeworkAnswerList(String homeworkId, Lifeful lifeful, ActionCallbackListener<List<HomeworkAnswerWithBLOBs>> listener);

    //提交作业评价
    void commitHomeworkEvaluation(String homeworkAnswerId, String exp, String remark, List<String> imgPathList, String delPhotoesUrl, String ifChangePhotoes, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //获取作业未评价的学生人数
    void getNotEvaluateStuNum(String homeworkId, Lifeful lifeful, ActionCallbackListener<Integer> listener);
}
