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
    public ApiResponse<List<Material>> getStuMaterial(String classid) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classid);
        Type type = new TypeToken<ApiResponse<List<Material>>>() {
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
}
