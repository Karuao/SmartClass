package team.qdu.core;

import java.io.File;
import java.util.Map;

/**
 * Created by 11602 on 2018/3/16.
 */

public interface FileAppAction {

    //缓存图片
    public void cacheImg(String urlTail, ActionCallbackListener<File> listener);

    //缓存文件
    public void cacheFile(String urlTail, ActionCallbackListener<File> listener);

    //获取版本更新信息
    public void getVersionInfo(ActionCallbackListener<Map> listener);

    //下载更新的App
    public void downloadApp(final String urlTail, final ActionCallbackListener<Object> listener);
}
