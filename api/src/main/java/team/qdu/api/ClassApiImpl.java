package team.qdu.api;

import team.qdu.api.net.HttpEngine;

/**
 * Created by 11602 on 2018/1/24.
 */

public class ClassApiImpl implements ClassApi {

    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private HttpEngine httpEngine;

    public ClassApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }
}
