package team.qdu.core;

import java.util.List;

import team.qdu.model.Class;

/**
 * Created by 11602 on 2018/1/24.
 */

public interface ClassAppAction {

    //获取登录用户加入的班课列表
    public void getJoinedClasses(String userId, ActionCallbackListener<List<Class>> listener);
}
