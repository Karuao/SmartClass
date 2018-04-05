package team.qdu.core;

import java.io.File;

/**
 * Created by 11602 on 2018/3/16.
 */

public interface ImgAppAction {

    //缓存图片
    public void cacheImg(String urlTail, ActionCallbackListener<File> listener);
    //缓存文件
    public void cacheFile(String urlTail,ActionCallbackListener<File> listener);
}
