package team.qdu.api;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import team.qdu.api.net.FileHttpEngine;
import team.qdu.api.net.HttpEngine;
import team.qdu.model.ApiResponse;
import team.qdu.model.User;

/**
 * Created by Rock on 2017/9/3.
 */

public class UserApiImpl implements UserApi {

    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "网络君似乎开小差了...";

    private HttpEngine httpEngine;
    private FileHttpEngine fileHttpEngine;

    public UserApiImpl() {
        httpEngine = HttpEngine.getInstance();
        fileHttpEngine = FileHttpEngine.getInstance();
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
    public ApiResponse<User> searchById(String userId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", userId);

        Type type = new TypeToken<ApiResponse<User>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/searchById");
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

    @Override
    public ApiResponse<Void> updateUserInformation(File avatar,String account, String name,String gender,String sno,String university,String department,
                                                   String motto){
        Map<String, String> paramMap = new HashMap<>();
        Map<String, File> fileMap = new HashMap<>();
        paramMap.put("account",account);
        paramMap.put("name", name);
        paramMap.put("gender",gender);
        paramMap.put("sno",sno);
        paramMap.put("university",university);
        paramMap.put("department",department);
        paramMap.put("motto",motto);
        if (avatar != null) {
            fileMap = new HashMap<>();
            fileMap.put("avatar", avatar);
        }
        Type type = new TypeToken<ApiResponse<Void>>() {
        }.getType();
        try {
            return fileHttpEngine.postHandle(paramMap,fileMap, type, "/updateUserInformation");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
}
