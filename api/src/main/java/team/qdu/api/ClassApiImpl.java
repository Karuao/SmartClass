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
import team.qdu.model.ClassUser;

/**
 * Created by 11602 on 2018/1/24.
 */

public class ClassApiImpl implements ClassApi {

    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "网络君似乎开小差了...";

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
    public ApiResponse<List<ClassUser>> getJoinedClasses(String userId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", userId);

        Type type = new TypeToken<ApiResponse<List<ClassUser>>>() {
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

    //获取加入班课成员列表
    @Override
    public ApiResponse<List<ClassUser>> getClassMembers(String classId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);

        Type type = new TypeToken<ApiResponse<List<ClassUser>>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getClassMembers");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<ClassUser> jumpClass(String classId, String userId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);
        paramMap.put("userId", userId);

        Type type = new TypeToken<ApiResponse<ClassUser>>() {
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

    public ApiResponse<Void> notAllowToJoin(String classId){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);
        Type type = new TypeToken<ApiResponse<Void>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/notAllowToJoin");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    public ApiResponse<Void> allowToJoin(String classId){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);
        Type type = new TypeToken<ApiResponse<Void>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/allowToJoin");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Class> searchByClassId(String classId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);

        Type type = new TypeToken<ApiResponse<Class>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getClassInfor");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> finishClass(String classId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);

        Type type = new TypeToken<ApiResponse<Class>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/finishClass");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> deleteClass(String classId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);

        Type type = new TypeToken<ApiResponse<Class>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/deleteClass");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
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
    public ApiResponse<String> modifyClass(String classId,File avatar,String className,String course,String university,String department,String goal,String exam){
        Map<String, String> paramMap = new HashMap<>();
        Map<String, File> fileMap = new HashMap<>();
        paramMap.put("classId",classId);
        paramMap.put("className", className);
        paramMap.put("course", course);
        paramMap.put("university",university);
        paramMap.put("department",department);
        paramMap.put("goal",goal);
        paramMap.put("exam",exam);
        fileMap.put("avatar", avatar);

        Type type = new TypeToken<ApiResponse<String>>() {
        }.getType();
        try {
            return fileHttpEngine.postHandle(paramMap, fileMap, type, "/modifyClass");
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

    public ApiResponse<Void> quitClass(String classId,String userId){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);
        paramMap.put("userId", userId);

        Type type = new TypeToken<ApiResponse<Void>>(){
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/quitClass");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public Void readNew(String classUserId, String whichPage) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classUserId", classUserId);
        paramMap.put("whichPage", whichPage);

        Type type = new TypeToken<ApiResponse<Void>>(){
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/readNew");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return null;
        }
    }
}
