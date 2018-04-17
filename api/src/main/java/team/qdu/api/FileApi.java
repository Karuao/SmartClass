package team.qdu.api;

import java.io.File;
import java.util.Map;

/**
 * Created by 11602 on 2018/3/16.
 */

public interface FileApi {

    File cacheFile(String urlTail, String localPath);

    Map getVersionInfo();
}
