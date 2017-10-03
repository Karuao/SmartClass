package team.qdu.api;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import team.qdu.api.net.HttpEngine;

/**
 * Created by Rock on 2017/9/3.
 */

public class ApiImpl implements Api {

    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private HttpEngine httpEngine;

    public ApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }

    @Override
    public ApiResponse<Void> loginByApp(String email, String password) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("email", email);
        paramMap.put("password", password);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        try {
            return httpEngine.postHandle(paramMap, type);
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG,"DEBUG",e.getMessage());
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
}
