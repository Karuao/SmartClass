package team.qdu.core;

import java.io.File;

/**
 * Created by 11602 on 2018/2/8.
 */

public interface HomeworkAppAction {

    //上传新发布作业的信息
    public void pushHomework(String title, String deadline, String detail, File photo, String classId, ActionCallbackListener<Void> listener);
}
