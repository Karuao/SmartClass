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
    public void login(String loginName, String password, ActionCallbackListener<Void> listener);

    public void register(String account, String password, String passwordConfirm, String question, String answer, boolean check, ActionCallbackListener<Void> actionCallbackListener);

    public void checkAccount(String account, ActionCallbackListener<User> listener);

    public void checkSecurityAnswer(String inputAnswer, String answer, ActionCallbackListener<Void> listener);

    public void modifyPass(String newPass, String newPassConfirm, String account, ActionCallbackListener<Void> listener);

    //获得用户信息
    public User getUserInfor(String account,ActionCallbackListener<User> listener);
}
