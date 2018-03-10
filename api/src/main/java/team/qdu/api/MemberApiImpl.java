package team.qdu.api;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.qdu.api.net.FileHttpEngine;
import team.qdu.api.net.HttpEngine;
import team.qdu.api.net.ImgHttpEngine;
import team.qdu.model.ApiResponse;
import team.qdu.model.ClassUser;

/**
 * Created by asus on 2018/3/7.
 */

public class MemberApiImpl implements MemberApi {
    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private HttpEngine httpEngine;
    private ImgHttpEngine imgHttpEngine;
    private FileHttpEngine fileHttpEngine;

    public MemberApiImpl() {
        httpEngine = HttpEngine.getInstance();
        imgHttpEngine = ImgHttpEngine.getInstance();
        fileHttpEngine = FileHttpEngine.getInstance();
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
    public Bitmap getBitmap(String urlTail) {
        try {
            return imgHttpEngine.getImg(urlTail);
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return null;
        }
    }
}
