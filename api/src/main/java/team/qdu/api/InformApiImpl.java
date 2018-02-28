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
import team.qdu.model.Inform;
import team.qdu.model.Inform_User;
import team.qdu.model.User;

/**
 * Created by n551 on 2018/1/29.
 */

public class InformApiImpl implements InformApi {
    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private HttpEngine httpEngine;

    public InformApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }
    @Override
    public ApiResponse<List<Inform>> getInform(String classid) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classid);
        Type type = new TypeToken<ApiResponse<List<Inform>>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getInform");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> getUnreadNum(String informid) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("informId", informid);
        Type type = new TypeToken<ApiResponse<Void>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getUnReadNum");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
    @Override
    public ApiResponse<List<Inform_User>> getUserInform(String classid,String userid) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classid);
        paramMap.put("userId", userid);
        Type type = new TypeToken<ApiResponse<List<Inform_User>>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getUserInform");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> ClickInform(String inform_user_id) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("informuserId",inform_user_id);
        Type type=new TypeToken<ApiResponse<Void>>(){
        }.getType();
        try{
            return httpEngine.postHandle(paramMap, type, "/ClickInform");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return new ApiResponse(TIME_OUT_EVENT,TIME_OUT_EVENT_MSG);
        }

    }

    @Override
    public ApiResponse<Void> deleteInform(String inform_id) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("informId",inform_id);
        Type type=new TypeToken<ApiResponse<Void>>(){
        }.getType();
        try{
            return httpEngine.postHandle(paramMap, type, "/deleteInform");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return new ApiResponse(TIME_OUT_EVENT,TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<List<User>> getReadPeople(String inform_id) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("informId", inform_id);
        Type type = new TypeToken<ApiResponse<List<User>>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getReadPeople");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
    @Override
    public ApiResponse<List<User>> getUnReadPeople(String inform_id) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("informId", inform_id);
        Type type = new TypeToken<ApiResponse<List<User>>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getUnReadPeople");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }


    @Override
    public ApiResponse<Void> createInform(String classid, String detail) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId",classid);
        paramMap.put("detail",detail);
        Type type=new TypeToken<ApiResponse<Void>>(){
        }.getType();
        try{
            return httpEngine.postHandle(paramMap, type, "/createInform");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return new ApiResponse(TIME_OUT_EVENT,TIME_OUT_EVENT_MSG);
        }

    }
}
