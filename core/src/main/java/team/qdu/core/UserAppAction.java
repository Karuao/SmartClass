package team.qdu.core;

import java.io.File;

import team.qdu.model.User;

/**
 * Created by Rock on 2017/9/5.
 */

public interface UserAppAction {

    /**
     * 登录
     *
     * @param loginName 登录名
     * @param password  密码
     * @param listener  回调监听器
     */
    void login(String loginName, String password, Lifeful lifeful, ActionCallbackListener<String> listener);

    void register(String account, String password, String passwordConfirm, String question, String answer, boolean check, Lifeful lifeful, ActionCallbackListener<Void> actionCallbackListener);

    void checkAccount(String account, Lifeful lifeful, ActionCallbackListener<User> listener);

    void checkSecurityAnswer(String inputAnswer, String answer, Lifeful lifeful, ActionCallbackListener<Void> listener);

    void modifyPass(String newPass, String newPassConfirm, String account, Lifeful lifeful, ActionCallbackListener<Void> listener);

    //获得用户信息（通过account）
    User getUserInforByAccount(String account, Lifeful lifeful, ActionCallbackListener<User> listener);

    //获得用户信息（通过account）
    User getUserInforById(String userId, Lifeful lifeful, ActionCallbackListener<User> listener);

    //修改用户个人信息
    void modifyUserInformation(File avatar, String account, String name, String gender, String sno, String university, String department
            , String motto, Lifeful lifeful, ActionCallbackListener<Void> listener);
}
