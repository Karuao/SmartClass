package team.qdu.core;

import team.qdu.model.User;

/**
 * Created by Rock on 2017/9/5.
 */

public interface AppAction {

    /**
     * 登录
     *
     * @param loginName 登录名
     * @param password  密码
     * @param listener  回调监听器
     */
    public void login(String loginName, String password, ActionCallbackListener<Void> listener);
    public void checkAccount(String account, ActionCallbackListener<User> listener);
}
