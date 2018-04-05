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
import team.qdu.model.Material;
import team.qdu.model.Material_User;

/**
 * Created by n551 on 2018/3/16.
 */

public class MaterialApiImpl implements MaterialApi {
    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "网络君似乎开小差了...";
    private HttpEngine httpEngine;

    public MaterialApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }

    @Override
    public ApiResponse<List<Material>> getTeaMaterial(String classid) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classid);
        Type type = new TypeToken<ApiResponse<List<Material>>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getTeaMaterial");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
    @Override
    public ApiResponse<Void> downloadMaterial(String classid,String userid,String name,String material_user_id) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classid",classid);
        paramMap.put("userid",userid);
        paramMap.put("name",name);
        paramMap.put("materialuserid",material_user_id);
        Type type=new TypeToken<ApiResponse<Void>>(){
        }.getType();
        try{
            return httpEngine.postHandle(paramMap, type, "/DownloadMaterial");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return new ApiResponse(TIME_OUT_EVENT,TIME_OUT_EVENT_MSG);
        }

    }
    @Override
    public ApiResponse<List<Material_User>> getStuMaterial(String classid,String userid) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classid);
        paramMap.put("userId", userid);
        Type type = new TypeToken<ApiResponse<List<Material_User>>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getStuMaterial");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
    @Override
    public ApiResponse<Void> deleteMaterial(String materialid) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("materialId",materialid);
        Type type=new TypeToken<ApiResponse<Void>>(){
        }.getType();
        try{
            return httpEngine.postHandle(paramMap, type, "/deleteMaterial");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return new ApiResponse(TIME_OUT_EVENT,TIME_OUT_EVENT_MSG);
        }
    }
}
