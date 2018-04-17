package team.qdu.core;

import java.util.List;

import team.qdu.model.Inform;
import team.qdu.model.Inform_User;
import team.qdu.model.User;

/**
 * Created by n551 on 2018/1/29.
 */

public interface InformAppAction {

    void getInform(String classid, Lifeful lifeful, ActionCallbackListener<List<Inform>> listener);

    void createInform(String classid, String detail, Lifeful lifeful, ActionCallbackListener<Void> listener);

    void getUserInform(String classid, String userid, Lifeful lifeful, ActionCallbackListener<List<Inform_User>> listener);

    void ClickInform(String inform_user_id, String classid, String userid, Lifeful lifeful, ActionCallbackListener<Void> listener);

    void deleteInform(String inform_id, Lifeful lifeful, ActionCallbackListener<Void> listener);

    void getReadPeople(String inform_id, Lifeful lifeful, ActionCallbackListener<List<User>> listener);

    void getUnreadNum(String inform_id, Lifeful lifeful, ActionCallbackListener<Void> listener);

    void getUnReadPeople(String inform_id, Lifeful lifeful, ActionCallbackListener<List<User>> listener);
}
