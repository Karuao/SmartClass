package team.qdu.api;

import java.util.List;

import team.qdu.model.ApiResponse;
import team.qdu.model.Inform;
import team.qdu.model.Inform_User;

/**
 * Created by n551 on 2018/1/29.
 */

public interface InformApi {

    public ApiResponse<List<Inform>> getInform(String classid);
    public ApiResponse<Void> createInform(String classid,String detail);
    public ApiResponse<List<Inform_User>> getUserInform(String classid, String userid);
    public ApiResponse<Void> ClickInform(String inform_user_id);
    public ApiResponse<Void> deleteInform(String inform_id);
}
