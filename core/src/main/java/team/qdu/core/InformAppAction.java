package team.qdu.core;

import java.util.List;

import team.qdu.model.Inform;
import team.qdu.model.Inform_User;
import team.qdu.model.User;

/**
 * Created by n551 on 2018/1/29.
 */

public interface InformAppAction {

    public void getInform(String classid,ActionCallbackListener<List<Inform>> listener);
    public void createInform(String classid,String detail,ActionCallbackListener<Void> listener);
    public void getUserInform(String classid,String userid,ActionCallbackListener<List<Inform_User>> listener);
    public void ClickInform(String inform_user_id,String classid,String userid,ActionCallbackListener<Void> listener);
    public void deleteInform(String inform_id,ActionCallbackListener<Void> listener);
    public void getReadPeople(String inform_id,ActionCallbackListener<List<User>> listener);
    public void getUnreadNum(String inform_id,ActionCallbackListener<Void> listener);
    public void getUnReadPeople(String inform_id,ActionCallbackListener<List<User>> listener);
}
