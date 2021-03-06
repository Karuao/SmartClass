package team.qdu.core;

import android.content.Context;

import java.io.File;
import java.util.Map;

/**
 * Created by 11602 on 2018/3/16.
 */

public interface FileAppAction {

    //缓存图片
    void cacheImg(String urlTail, Context context, ActionCallbackListener<File> listener);

    //缓存文件
    void cacheFile(String urlTail, Context context, ActionCallbackListener<File> listener);

    //获取版本更新信息
    void getVersionInfo(ActionCallbackListener<Map> listener);

    //下载更新的App
    void downloadApp(final String urlTail, Context context, final ActionCallbackListener<Object> listener);
}
