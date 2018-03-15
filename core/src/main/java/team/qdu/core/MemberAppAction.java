package team.qdu.core;

import android.graphics.Bitmap;

import java.util.List;

import team.qdu.model.ClassUser;

/**
 * Created by asus on 2018/3/7.
 */

public interface MemberAppAction {

    //获取某个班课成员列表
    public void getClassMembers(String classId, ActionCallbackListener<List<ClassUser>> listener);

    //获取登录用户的信息
    public ClassUser getMemberInfo(String classUserId, ActionCallbackListener<ClassUser> listener);

    //获取图片
    public void getBitmap(String urlTail, ActionCallbackListener<Bitmap> listener);

    //移出班课
    public void shiftClass(String classUserId,ActionCallbackListener<Void> listener);


}
