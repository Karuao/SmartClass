package team.qdu.core;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import team.qdu.api.Api;
import team.qdu.api.ApiImpl;
import team.qdu.model.ApiResponse;

/**
 * Created by Rock on 2017/9/5.
 */

public class AppActionImpl implements AppAction {

    private final static int LOGIN_OS = 1;

    private Context context;
    private Api api;

    public AppActionImpl(Context context) {
        this.context = context;
        this.api = new ApiImpl();
    }

    @Override
    public void login(final String email, final String password, final ActionCallbackListener<Void> listener) {
        //参数检查
        if (TextUtils.isEmpty(email)) {
                if (listener != null) {
                    listener.onFailure(ErrorEvent.PARAM_NULL, "登录名为空");
                }
                return;
            }
            if (TextUtils.isEmpty(password)) {
                if (listener != null) {
                    listener.onFailure(ErrorEvent.PARAM_NULL, "密码为空");
                }
                return;
        }

        //请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {

            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return api.loginByApp(email, password);
            }

            @Override
            protected  void onPostExecute(ApiResponse<Void> response) {
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
}
