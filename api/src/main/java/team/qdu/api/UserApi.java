package team.qdu.api;

import java.io.File;

import team.qdu.model.ApiResponse;
import team.qdu.model.User;

/**
 * Created by Rock on 2017/9/3.
 */

public interface UserApi {

    ApiResponse<String> loginByApp(String loginName, String password);

    ApiResponse<Void> registerByApp(String account, String password, String question, String answer);

    ApiResponse<User> searchByAccount(String account);

    ApiResponse<User> searchById(String account);

    ApiResponse<Void> updatePassword(String account, String newPass);

    ApiResponse<Void> updateUserInformation(File avatar, String account, String name, String gender, String sno, String university, String department,
                                            String motto);
}
