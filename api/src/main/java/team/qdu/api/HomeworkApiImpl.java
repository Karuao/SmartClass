package team.qdu.api;

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
import team.qdu.model.Homework;
import team.qdu.model.HomeworkAnswerWithBLOBs;

/**
 * Created by 11602 on 2018/2/8.
 */

public class HomeworkApiImpl implements HomeworkApi {

    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private HttpEngine httpEngine;
    private ImgHttpEngine imgHttpEngine;
    private FileHttpEngine fileHttpEngine;

    public HomeworkApiImpl() {
        httpEngine = HttpEngine.getInstance();
        imgHttpEngine = ImgHttpEngine.getInstance();
        fileHttpEngine = FileHttpEngine.getInstance();
    }

    @Override
    public ApiResponse<Void> publishHomework(String title, String deadline, String detail, File photo, String classId) {
        Map<String, String> paramMap = new HashMap<>();
        Map<String, File> fileMap = null;
        paramMap.put("title", title);
        paramMap.put("deadline", deadline);
        paramMap.put("detail", detail);
        paramMap.put("classId", classId);
        if (photo != null) {
            fileMap = new HashMap<>();
            fileMap.put("photo", photo);
        }

        Type type = new TypeToken<ApiResponse<String>>() {
        }.getType();
        try {
            return fileHttpEngine.postHandle(paramMap, fileMap, type, "/publishHomework");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<List> getHomeworkList(String classId, String userId, String userTitle, String requestStatus) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("classId", classId);
        paramMap.put("userId", userId);
        paramMap.put("userTitle", userTitle);
        paramMap.put("requestStatus", requestStatus);
        Type type;

        if ("teacher".equals(userTitle)) {
            type = new TypeToken<ApiResponse<List<Homework>>>() {
            }.getType();
        } else {
            type = new TypeToken<ApiResponse<List<HomeworkAnswerWithBLOBs>>>() {
            }.getType();
        }

        try {
            return httpEngine.postHandle(paramMap, type, "/getHomeworkList");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> changeHomeworkStatus(String homeworkId, String homeworkStatus) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("homeworkId", homeworkId);
        paramMap.put("homeworkStatus", homeworkStatus);

        Type type = new TypeToken<ApiResponse<Void>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/changeHomeworkStatus");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<HomeworkAnswerWithBLOBs> getStuHomeworkDetail(String homeworkAnswerId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("homeworkAnswerId", homeworkAnswerId);

        Type type = new TypeToken<ApiResponse<HomeworkAnswerWithBLOBs>>() {
        }.getType();
        try {
            return httpEngine.postHandle(paramMap, type, "/getStuHomeworkDetail");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> submitHomework(String homeworkAnswerId, String detail, File answerPhoto) {
        Map<String, String> paramMap = new HashMap<>();
        Map<String, File> fileMap = null;
        paramMap.put("homeworkAnswerId", homeworkAnswerId);
        paramMap.put("detail", detail);
        if (answerPhoto != null) {
            fileMap = new HashMap<>();
            fileMap.put("photo", answerPhoto);
        }

        Type type = new TypeToken<ApiResponse<String>>() {
        }.getType();
        try {
            return fileHttpEngine.postHandle(paramMap, fileMap, type, "/submitHomework");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            //返回连接服务器失败
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
}