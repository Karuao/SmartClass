package team.qdu.api;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import team.qdu.api.net.HttpEngine;
import team.qdu.model.ApiResponse;
import team.qdu.model.User;

/**
 * Created by Rock on 2017/9/3.
 */

public class UserApiImpl implements UserApi {

    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private HttpEngine httpEngine;

    public UserApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }

    @Override
    public ApiResponse<String> loginByApp(String account, String password) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("account", account);
        paramMap.put("password", password);

        Type type = new TypeToken<ApiResponse<String>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/login");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> registerByApp(String account, String password, String question, String answer) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("account", account);
        paramMap.put("password", password);
        paramMap.put("question", question);
        paramMap.put("answer", answer);

        Type type = new TypeToken<ApiResponse<Void>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/register");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<User> searchByAccount(String account) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("account", account);

        Type type = new TypeToken<ApiResponse<User>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/findPassOne");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> updatePassword(String account, String newPass) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("account",account);
        paramMap.put("newPass", newPass);
        Type type = new TypeToken<ApiResponse<Void>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/updatePass");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
}
