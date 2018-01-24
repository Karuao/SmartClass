package team.qdu.core;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import team.qdu.api.UserApi;
import team.qdu.api.UserApiImpl;
import team.qdu.model.ApiResponse;
import team.qdu.model.User;

/**
 * Created by Rock on 2017/9/5.
 */

public class UserAppActionImpl implements UserAppAction {

    private Context context;
    private UserApi userApi;

    public UserAppActionImpl(Context context) {
        this.context = context;
        this.userApi = new UserApiImpl();
    }

    @Override
    public void login(final String account, final String password, final ActionCallbackListener<Void> listener) {
        //参数检查
        if (TextUtils.isEmpty(account)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "登录名为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "密码为空");
            return;
        }

        //请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return userApi.loginByApp(account, password);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(null, response.getMsg());
                    } else {
                        listener.onFailure(response.getEvent(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    //找回密码时检验账号是否存在
    @Override
    public void register(final String account, final String password, final String passwordConfirm, final String question, final String answer, final boolean check, final ActionCallbackListener<Void> listener) {
        //参数检查
        if (TextUtils.isEmpty(account)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "登录名为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "密码为空");
            return;
        }
        if (!password.equals(passwordConfirm)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "请确认密码一致");
            return;
        }
        if (TextUtils.isEmpty(answer)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "回答为空");
            return;
        }
        if (!check) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "请点击同意协议");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return userApi.registerByApp(account, password, question, answer);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(null, response.getMsg());
                    } else {
                        listener.onFailure(response.getEvent(), response.getMsg());
                    }
                }
            }
        }.execute();
    }


    @Override
    public void checkAccount(final String account, final ActionCallbackListener<User> listener) {
        //判断是否为空
        if (TextUtils.isEmpty(account)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "用户名为空");
            return;
        }

        //请求Api
        new AsyncTask<Void, Void, ApiResponse<User>>() {
            @Override
            protected ApiResponse<User> doInBackground(Void... params) {
                return userApi.searchByAccount(account);
            }

            @Override
            protected void onPostExecute(ApiResponse<User> response) {
                if (listener != null && response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getObj(), response.getMsg());
                    } else {
                        listener.onFailure(response.getEvent(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    //判断密保问题的答案是否正确
    @Override
    public void checkSecurityAnswer(final String inputAnswer, final String answer, final ActionCallbackListener listener) {
        //判断是否为空
        if (TextUtils.isEmpty(inputAnswer)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "请输入答案");
            return;
        } else {
            if (inputAnswer.equals(answer)) {
                listener.onSuccess(null, "验证成功");
            } else {
                listener.onFailure(ErrorEvent.PARAM_NULL, "验证失败");
            }
        }
    }

    @Override
    public void modifyPass(final String pass, final String passConfirm, final int userId, final ActionCallbackListener<Void> listener) {
        //判断是否为空
        if (TextUtils.isEmpty(pass)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(passConfirm)) {
            listener.onFailure(ErrorEvent.PARAM_NULL, "请再次确认新密码");
        } else if (pass.equals(passConfirm)) {
            //请求Api
            new AsyncTask<Void, Void, ApiResponse<Void>>() {
                @Override
                protected ApiResponse<Void> doInBackground(Void... params) {
                    return userApi.updatePassword(userId, passConfirm);
                }

                @Override
                protected void onPostExecute(ApiResponse<Void> response) {
                    if (listener != null && response != null) {
                        if (response.isSuccess()) {
                            listener.onSuccess(null, response.getMsg());
                        } else {
                            listener.onFailure(response.getEvent(), response.getMsg());
                        }
                    }
                }
            }.execute();
        } else {
            listener.onFailure(ErrorEvent.PARAM_NULL, "两次请输入相同的密码");
        }
    }
}

