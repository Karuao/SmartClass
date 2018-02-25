package team.qdu.core;

import java.io.File;
import java.util.List;

import team.qdu.model.Homework;

/**
 * Created by 11602 on 2018/2/8.
 */

public interface HomeworkAppAction {

    //上传新发布作业的信息
    public void pushHomework(String title, String deadline, String detail, File photo, String classId, ActionCallbackListener<Void> listener);

    //获取班课列表
    public void getHomeworkList(String classId, String userId, String userTitle, String requestStatus, ActionCallbackListener<List<Homework>> listener);
}
