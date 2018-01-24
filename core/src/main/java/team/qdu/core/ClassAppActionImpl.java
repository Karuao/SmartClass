package team.qdu.core;

import android.content.Context;

import team.qdu.api.UserApi;
import team.qdu.api.UserApiImpl;

/**
 * Created by 11602 on 2018/1/24.
 */

public class ClassAppActionImpl implements ClassAppAction {

    private Context context;
    private UserApi userApi;

    public ClassAppActionImpl(Context context) {
        this.context = context;
        this.userApi = new UserApiImpl();
    }
}
