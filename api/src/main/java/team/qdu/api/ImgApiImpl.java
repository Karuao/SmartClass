package team.qdu.api;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import team.qdu.api.net.ImgCacheHttpEngine;

/**
 * Created by 11602 on 2018/3/16.
 */

public class ImgApiImpl implements ImgApi {

    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "网络君似乎开小差了...";

    private ImgCacheHttpEngine imgCacheHttpEngine;

    public ImgApiImpl() {
        imgCacheHttpEngine = ImgCacheHttpEngine.getInstance();
    }

    //从网络缓存图片
    @Override
    public File cacheImg(String urlTail) {
        try {
            return imgCacheHttpEngine.cacheImg(urlTail);
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return null;
        }
    }

    @Override
    public File cacheFile(String urlTail) {

        try {
            return imgCacheHttpEngine.cacheImg(urlTail);
        } catch (IOException e) {
            e.printStackTrace();
            Log.println(Log.DEBUG, "DEBUG", e.getMessage());
            return null;
        }
    }
}
