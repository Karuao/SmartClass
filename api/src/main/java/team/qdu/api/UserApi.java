package team.qdu.api;

import android.graphics.Bitmap;

import java.io.File;

import team.qdu.model.ApiResponse;
import team.qdu.model.User;

/**
 * Created by Rock on 2017/9/3.
 */

public interface UserApi {

    public Bitmap getBitmap(String urlTail);

    public ApiResponse<String> loginByApp(String loginName, String password);

    public ApiResponse<Void> registerByApp(String account, String password, String question, String answer);

    public ApiResponse<User> searchByAccount(String account);

    public ApiResponse<User> searchById(String account);

    public ApiResponse<Void> updatePassword(String account, String newPass);

    public ApiResponse<Void> updateUserInformation(File avatar,String account, String name, String gender, String sno,String university, String department,
                                                   String motto);
}
