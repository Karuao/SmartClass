package team.qdu.api;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.qdu.api.net.FileHttpEngine;
import team.qdu.api.net.HttpEngine;
import team.qdu.api.net.ImgHttpEngine;
import team.qdu.model.ApiResponse;
import team.qdu.model.Class;

/**
 * Created by 11602 on 2018/1/24.
 */

public class ClassApiImpl implements ClassApi {

    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private HttpEngine httpEngine;
    private ImgHttpEngine imgHttpEngine;
    private FileHttpEngine fileHttpEngine;

    public ClassApiImpl() {
        httpEngine = HttpEngine.getInstance();
        imgHttpEngine = ImgHttpEngine.getInstance();
        fileHttpEngine = FileHttpEngine.getInstance();
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

    @Override
    public Bitmap getBitmap(String urlTail) {
        try {
            return imgHttpEngine.getImg(urlTail);
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return null;
        }
    }

    @Override
    public ApiResponse<String> createClass(File avatar, String name, String course, String userId) {
        Map<String, String> paramMap = new HashMap<>();
        Map<String, File> fileMap = new HashMap<>();
        paramMap.put("name", name);
        paramMap.put("course", course);
        paramMap.put("userId", userId);
        fileMap.put("avatar", avatar);

        Type type = new TypeToken<ApiResponse<String>>() {
        }.getType();
        try {
            return fileHttpEngine.postHandle(paramMap, fileMap, type, "/createClass");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Class> joinClass(String classId, String userId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);
        paramMap.put("userId", userId);

        Type type = new TypeToken<ApiResponse<Class>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/joinClass");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    public ApiResponse<Void> confirmJoinClass(String classId, String userId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);
        paramMap.put("userId", userId);

        Type type = new TypeToken<ApiResponse<Void>>(){
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/confirmJoinClass");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
}
