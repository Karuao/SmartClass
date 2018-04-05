package team.qdu.api;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import team.qdu.api.net.FileCacheHttpEngine;
import team.qdu.api.net.HttpEngine;

/**
 * Created by 11602 on 2018/3/16.
 */

public class FileApiImpl implements FileApi {

    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "网络君似乎开小差了...";

    private FileCacheHttpEngine fileCacheHttpEngine;
    private HttpEngine httpEngine;

    public FileApiImpl() {
        fileCacheHttpEngine = FileCacheHttpEngine.getInstance();
        httpEngine = HttpEngine.getInstance();
    }

    //从网络缓存图片
    @Override
    public File cacheImg(String urlTail) {
        try {
            return fileCacheHttpEngine.cacheImg(urlTail);
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return null;
        }
    }

    @Override
    public File cacheFile(String urlTail) {
        try {
            return fileCacheHttpEngine.cacheImg(urlTail);
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return null;
        }
    }

    @Override
    public Map getVersionInfo() {
        Type type = new TypeToken<HashMap>() {
        }.getType();
        try {
            return httpEngine.postHandle(null, type, "/getVersionInfo");
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return null;
        }
    }
}
