package team.qdu.api;

import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.Inform;
import team.qdu.model.Inform_User;
import team.qdu.model.User;

/**
 * Created by n551 on 2018/1/29.
 */

public interface InformApi {

    ApiResponse<List<Inform>> getInform(String classid);
    ApiResponse<Void> createInform(String classid, String detail);
    ApiResponse<List<Inform_User>> getUserInform(String classid, String userid);
    ApiResponse<Void> ClickInform(String inform_user_id, String classid, String userid);
    ApiResponse<Void> deleteInform(String inform_id);
    ApiResponse<List<User>> getReadPeople(String inform_id);
    ApiResponse<List<User>> getUnReadPeople(String inform_id);
    ApiResponse<Inform> getUnreadNum(String informid);
}
