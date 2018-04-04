package team.qdu.api;

import java.io.File;
import java.util.Map;

/**
 * Created by 11602 on 2018/3/16.
 */

public interface FileApi {

    public File cacheImg(String urlTail);

    public File cacheFile(String urlTail);

    public Map getVersionInfo();
}
