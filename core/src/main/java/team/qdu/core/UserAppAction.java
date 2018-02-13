package team.qdu.core;

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
    public void login(String loginName, String password, ActionCallbackListener<String> listener);

    public void register(String account, String password, String passwordConfirm, String question, String answer, boolean check, ActionCallbackListener<Void> actionCallbackListener);

    public void checkAccount(String account, ActionCallbackListener<User> listener);

    public void checkSecurityAnswer(String inputAnswer, String answer, ActionCallbackListener<Void> listener);

    public void modifyPass(String newPass, String newPassConfirm, String account, ActionCallbackListener<Void> listener);

    //获得用户信息（通过account）
    public User getUserInforByAccount(String account,ActionCallbackListener<User> listener);

    //获得用户信息（通过account）
    public User getUserInforById(String userId,ActionCallbackListener<User> listener);

    //修改用户个人信息
    public void modifyUserInformation(String account,String name,String gender,String university,String department
                                      ,String motto,ActionCallbackListener<Void> listener);
}
