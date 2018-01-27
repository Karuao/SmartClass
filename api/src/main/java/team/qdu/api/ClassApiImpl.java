package team.qdu.api;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.qdu.api.net.HttpEngine;
import team.qdu.model.ApiResponse;
import team.qdu.model.Class;

/**
 * Created by 11602 on 2018/1/24.
 */

public class ClassApiImpl implements ClassApi {

    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private HttpEngine httpEngine;

    public ClassApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }

    //获取登录用户加入的班课列表
    @Override
    public ApiResponse<List<Class>> getJoinedClasses(String userId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", userId);

        Type type = new TypeToken<ApiResponse<List<Class>>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getJoinedClasses");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> jumpClass(String classId, String userId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);
        paramMap.put("userId", userId);

        Type type = new TypeToken<ApiResponse<Void>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/jumpClass");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
}
